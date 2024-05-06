package com.galuhrmdh.simpegrestapi.service;

import com.galuhrmdh.simpegrestapi.entity.Contract;
import com.galuhrmdh.simpegrestapi.entity.Employee;
import com.galuhrmdh.simpegrestapi.entity.WorkExperience;
import com.galuhrmdh.simpegrestapi.model.ListRequest;
import com.galuhrmdh.simpegrestapi.model.SavedResponse;
import com.galuhrmdh.simpegrestapi.model.contract.ContractReminderRequest;
import com.galuhrmdh.simpegrestapi.model.contract.ContractResponse;
import com.galuhrmdh.simpegrestapi.model.contract.CreateContractRequest;
import com.galuhrmdh.simpegrestapi.model.contract.UpdateContractRequest;
import com.galuhrmdh.simpegrestapi.repository.ContractRepository;
import com.galuhrmdh.simpegrestapi.repository.EmployeeRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ContractService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ValidationService validationService;

    private ContractResponse toContractResponse(Contract contract) {
        return ContractResponse.builder()
                .id(contract.getId())
                .employee(contract.getEmployee())
                .contractStatus(contract.getContractStatus())
                .startDate(contract.getStartDate())
                .contractLengthMonth(contract.getContractLengthMonth())
                .attachment(contract.getAttachment())
                .build();
    }

    @Transactional(readOnly = true)
    public Page<ContractResponse> list(ListRequest request) {
        Employee employee;

        if (Objects.nonNull(request.getEmployeeId())) {
            employee = employeeRepository.findById(request.getEmployeeId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee ID is not found"));
        } else {
            employee = null;
        }

        Specification<Contract> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(employee)) {
                predicates.add(builder.equal(root.get("employee"), employee));
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Contract> contracts = contractRepository.findAll(specification, pageable);

        List<ContractResponse> contractResponses = contracts.stream().map(this::toContractResponse).toList();

        return new PageImpl<>(contractResponses, pageable, contracts.getTotalElements());
    }

    @Transactional
    public SavedResponse create(CreateContractRequest request) {
        validationService.validate(request);

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee ID is not found"));

        Contract contract = new Contract();
        contract.setEmployee(employee);
        contract.setContractStatus(request.getContractStatus());
        contract.setStartDate(request.getStartDate());
        contract.setContractLengthMonth(request.getContractLengthMonth());
        contract.setAttachment(request.getAttachment());

        Contract savedContract = contractRepository.save(contract);

        return SavedResponse.builder()
                .id(savedContract.getId())
                .label(employee.getName())
                .build();
    }

    @Transactional
    public SavedResponse update(UpdateContractRequest request) {
        validationService.validate(request);

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee ID is not found"));

        Contract contract = contractRepository.findById(request.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        contract.setEmployee(employee);
        contract.setContractStatus(request.getContractStatus());
        contract.setStartDate(request.getStartDate());
        contract.setContractLengthMonth(request.getContractLengthMonth());
        contract.setAttachment(request.getAttachment());

        Contract savedContract = contractRepository.save(contract);

        return SavedResponse.builder()
                .id(savedContract.getId())
                .label(employee.getName())
                .build();
    }

    @Transactional
    public void delete(Integer id) {
        Contract contract = contractRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        contractRepository.delete(contract);
    }

    public List<Map<String, Object>> getContractsReminder(ContractReminderRequest request) {
        return contractRepository.getContractReminder(request.getStartDate(), request.getEndDate());
    }

}
