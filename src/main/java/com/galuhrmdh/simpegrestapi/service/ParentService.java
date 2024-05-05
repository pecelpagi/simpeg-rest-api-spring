package com.galuhrmdh.simpegrestapi.service;

import com.galuhrmdh.simpegrestapi.entity.Employee;
import com.galuhrmdh.simpegrestapi.entity.Parent;
import com.galuhrmdh.simpegrestapi.model.ListRequest;
import com.galuhrmdh.simpegrestapi.model.SavedResponse;
import com.galuhrmdh.simpegrestapi.model.parent.CreateParentRequest;
import com.galuhrmdh.simpegrestapi.model.parent.ParentResponse;
import com.galuhrmdh.simpegrestapi.model.parent.UpdateParentRequest;
import com.galuhrmdh.simpegrestapi.repository.EmployeeRepository;
import com.galuhrmdh.simpegrestapi.repository.ParentRepository;
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
public class ParentService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ValidationService validationService;

    private ParentResponse toParentResponse(Parent parent) {
        return ParentResponse.builder()
                .id(parent.getId())
                .idNumber(parent.getIdNumber())
                .employee(parent.getEmployee())
                .name(parent.getName())
                .birthplace(parent.getBirthplace())
                .birthdate(parent.getBirthdate())
                .gender(parent.getGender())
                .lastEducation(parent.getLastEducation())
                .occupation(parent.getOccupation())
                .parentStatus(parent.getParentStatus())
                .build();
    }

    @Transactional(readOnly = true)
    public Page<ParentResponse> list(ListRequest request) {
        Employee employee;

        if (Objects.nonNull(request.getEmployeeId())) {
            employee = employeeRepository.findById(request.getEmployeeId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee ID is not found"));
        } else {
            employee = null;
        }

        Specification<Parent> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(employee)) {
                predicates.add(builder.equal(root.get("employee"), employee));
            }

            if (Objects.nonNull(request.getSearch())) {
                predicates.add(builder.or(
                        builder.like(root.get("idNumber"), "%" + request.getSearch() + "%"),
                        builder.like(root.get("name"), "%" + request.getSearch() + "%"),
                        builder.like(root.get("birthplace"), "%" + request.getSearch() + "%"),
                        builder.like(root.get("occupation"), "%" + request.getSearch() + "%")
                ));
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Parent> parent = parentRepository.findAll(specification, pageable);

        List<ParentResponse> parentResponses = parent.stream().map(this::toParentResponse).toList();

        return new PageImpl<>(parentResponses, pageable, parent.getTotalElements());
    }

    @Transactional
    public SavedResponse create(CreateParentRequest request) {
        validationService.validate(request);

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee ID is not found"));

        Parent parent = new Parent();
        parent.setEmployee(employee);
        parent.setIdNumber(request.getIdNumber());
        parent.setName(request.getName());
        parent.setBirthplace(request.getBirthplace());
        parent.setBirthdate(request.getBirthdate());
        parent.setGender(request.getGender());
        parent.setLastEducation(request.getLastEducation());
        parent.setOccupation(request.getOccupation());
        parent.setParentStatus(request.getParentStatus());

        Parent savedParent = parentRepository.save(parent);

        return SavedResponse.builder()
                .id(savedParent.getId())
                .label(savedParent.getName())
                .build();
    }

    @Transactional
    public SavedResponse update(UpdateParentRequest request) {
        validationService.validate(request);

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee ID is not found"));

        Parent parent = parentRepository.findById(request.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        parent.setEmployee(employee);
        parent.setIdNumber(request.getIdNumber());
        parent.setName(request.getName());
        parent.setBirthplace(request.getBirthplace());
        parent.setBirthdate(request.getBirthdate());
        parent.setGender(request.getGender());
        parent.setLastEducation(request.getLastEducation());
        parent.setOccupation(request.getOccupation());
        parent.setParentStatus(request.getParentStatus());

        Parent savedParent = parentRepository.save(parent);

        return SavedResponse.builder()
                .id(savedParent.getId())
                .label(savedParent.getName())
                .build();
    }

    @Transactional
    public void delete(Integer id) {
        Parent parent = parentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        parentRepository.delete(parent);
    }

}
