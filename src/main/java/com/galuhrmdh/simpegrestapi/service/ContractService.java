package com.galuhrmdh.simpegrestapi.service;

import com.galuhrmdh.simpegrestapi.entity.Contract;
import com.galuhrmdh.simpegrestapi.entity.Department;
import com.galuhrmdh.simpegrestapi.entity.Employee;
import com.galuhrmdh.simpegrestapi.model.SavedResponse;
import com.galuhrmdh.simpegrestapi.model.UpdateDepartmentRequest;
import com.galuhrmdh.simpegrestapi.model.contract.CreateContractRequest;
import com.galuhrmdh.simpegrestapi.model.contract.UpdateContractRequest;
import com.galuhrmdh.simpegrestapi.repository.ContractRepository;
import com.galuhrmdh.simpegrestapi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ContractService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ValidationService validationService;

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

}
