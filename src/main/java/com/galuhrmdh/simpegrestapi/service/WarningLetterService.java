package com.galuhrmdh.simpegrestapi.service;

import com.galuhrmdh.simpegrestapi.entity.Employee;
import com.galuhrmdh.simpegrestapi.entity.WarningLetter;
import com.galuhrmdh.simpegrestapi.model.ListRequest;
import com.galuhrmdh.simpegrestapi.model.SavedResponse;
import com.galuhrmdh.simpegrestapi.model.warningletter.CreateWarningLetterRequest;
import com.galuhrmdh.simpegrestapi.model.warningletter.UpdateWarningLetterRequest;
import com.galuhrmdh.simpegrestapi.model.warningletter.WarningLetterResponse;
import com.galuhrmdh.simpegrestapi.repository.EmployeeRepository;
import com.galuhrmdh.simpegrestapi.repository.WarningLetterRepository;
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
import java.util.Objects;

@Service
public class WarningLetterService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private WarningLetterRepository warningLetterRepository;

    @Autowired
    private ValidationService validationService;

    private WarningLetterResponse toWarningLetterResponse(WarningLetter warningLetter) {
        return WarningLetterResponse.builder()
                .id(warningLetter.getId())
                .employee(warningLetter.getEmployee())
                .dateFacingHrd(warningLetter.getDateFacingHrd())
                .regarding(warningLetter.getRegarding())
                .violation1(warningLetter.getViolation1())
                .violation2(warningLetter.getViolation2())
                .suspensionPeriod(warningLetter.getSuspensionPeriod())
                .build();
    }

    @Transactional(readOnly = true)
    public Page<WarningLetterResponse> list(ListRequest request) {
        Specification<WarningLetter> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(request.getSearch())) {
                predicates.add(builder.or(
                        builder.like(root.get("violation1"), "%" + request.getSearch() + "%"),
                        builder.like(root.get("violation2"), "%" + request.getSearch() + "%")
                ));
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<WarningLetter> warningLetters = warningLetterRepository.findAll(specification, pageable);

        List<WarningLetterResponse> warningLetterResponses = warningLetters.stream().map(this::toWarningLetterResponse).toList();

        return new PageImpl<>(warningLetterResponses, pageable, warningLetters.getTotalElements());
    }

    @Transactional
    public SavedResponse create(CreateWarningLetterRequest request) {
        validationService.validate(request);

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee ID is not found"));

        WarningLetter warningLetter = new WarningLetter();
        warningLetter.setEmployee(employee);
        warningLetter.setDateFacingHrd(request.getDateFacingHrd());
        warningLetter.setRegarding(request.getRegarding());
        warningLetter.setViolation1(request.getViolation1());
        warningLetter.setViolation2(request.getViolation2());
        warningLetter.setSuspensionPeriod(request.getSuspensionPeriod());

        WarningLetter savedWarningLetter = warningLetterRepository.save(warningLetter);

        return SavedResponse.builder()
                .id(savedWarningLetter.getId())
                .label(employee.getName())
                .build();
    }

    @Transactional
    public SavedResponse update(UpdateWarningLetterRequest request) {
        validationService.validate(request);

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee ID is not found"));

        WarningLetter warningLetter = warningLetterRepository.findById(request.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        warningLetter.setEmployee(employee);
        warningLetter.setDateFacingHrd(request.getDateFacingHrd());
        warningLetter.setRegarding(request.getRegarding());
        warningLetter.setViolation1(request.getViolation1());
        warningLetter.setViolation2(request.getViolation2());
        warningLetter.setSuspensionPeriod(request.getSuspensionPeriod());

        WarningLetter savedWarningLetter = warningLetterRepository.save(warningLetter);

        return SavedResponse.builder()
                .id(savedWarningLetter.getId())
                .label(employee.getName())
                .build();
    }

    @Transactional
    public void delete(Integer id) {
        WarningLetter warningLetter = warningLetterRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        warningLetterRepository.delete(warningLetter);
    }
    
}
