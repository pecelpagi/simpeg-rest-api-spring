package com.galuhrmdh.simpegrestapi.service;

import com.galuhrmdh.simpegrestapi.entity.Department;
import com.galuhrmdh.simpegrestapi.entity.EmployeePosition;
import com.galuhrmdh.simpegrestapi.model.CreateDepartmentRequest;
import com.galuhrmdh.simpegrestapi.model.ListRequest;
import com.galuhrmdh.simpegrestapi.model.SavedResponse;
import com.galuhrmdh.simpegrestapi.model.UpdateDepartmentRequest;
import com.galuhrmdh.simpegrestapi.model.employeeposition.CreateEmployeePositionRequest;
import com.galuhrmdh.simpegrestapi.model.employeeposition.EmployeePositionResponse;
import com.galuhrmdh.simpegrestapi.model.employeeposition.UpdateEmployeePositionRequest;
import com.galuhrmdh.simpegrestapi.repository.EmployeePositionRepository;
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

@Service
public class EmployeePositionService {

    @Autowired
    private EmployeePositionRepository employeePositionRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional(readOnly = true)
    public Page<EmployeePositionResponse> list(ListRequest request) {
        Specification<EmployeePosition> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(builder.like(root.get("name"), "%" + request.getSearch() + "%"));

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<EmployeePosition> employeePositions = employeePositionRepository.findAll(specification, pageable);

        List<EmployeePositionResponse> employeePositionResponses = employeePositions.stream()
                .map((EmployeePosition employeePosition) -> EmployeePositionResponse.builder()
                        .id(employeePosition.getId())
                        .name(employeePosition.getName())
                        .build())
                .toList();

        return new PageImpl<>(employeePositionResponses, pageable, employeePositions.getTotalElements());
    }

    @Transactional
    public SavedResponse create(CreateEmployeePositionRequest request) {
        validationService.validate(request);

        EmployeePosition employeePosition = new EmployeePosition();
        employeePosition.setName(request.getName());

        employeePosition = employeePositionRepository.save(employeePosition);

        return SavedResponse.builder()
                .id(employeePosition.getId())
                .label(employeePosition.getName())
                .build();
    }

    @Transactional
    public SavedResponse update(UpdateEmployeePositionRequest request) {
        validationService.validate(request);

        EmployeePosition employeePosition = employeePositionRepository.findById(request.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        employeePosition.setName(request.getName());

        employeePosition = employeePositionRepository.save(employeePosition);

        return SavedResponse.builder()
                .id(employeePosition.getId())
                .label(employeePosition.getName())
                .build();
    }

    @Transactional
    public void delete(Integer id) {
        EmployeePosition employeePosition = employeePositionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        employeePositionRepository.delete(employeePosition);
    }
}
