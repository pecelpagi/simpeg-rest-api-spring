package com.galuhrmdh.simpegrestapi.service;

import com.galuhrmdh.simpegrestapi.entity.Employee;
import com.galuhrmdh.simpegrestapi.entity.EmployeePosition;
import com.galuhrmdh.simpegrestapi.entity.WorkExperience;
import com.galuhrmdh.simpegrestapi.model.ListRequest;
import com.galuhrmdh.simpegrestapi.model.SavedResponse;
import com.galuhrmdh.simpegrestapi.model.workexperience.CreateWorkExperienceRequest;
import com.galuhrmdh.simpegrestapi.model.workexperience.UpdateWorkExperienceRequest;
import com.galuhrmdh.simpegrestapi.model.workexperience.WorkExperienceResponse;
import com.galuhrmdh.simpegrestapi.repository.EmployeePositionRepository;
import com.galuhrmdh.simpegrestapi.repository.EmployeeRepository;
import com.galuhrmdh.simpegrestapi.repository.WorkExperienceRepository;
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
public class WorkExperienceService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeePositionRepository employeePositionRepository;

    @Autowired
    private WorkExperienceRepository workExperienceRepository;

    @Autowired
    private ValidationService validationService;

    private WorkExperienceResponse toWorkExperienceResponse(WorkExperience workExperience) {
        return WorkExperienceResponse.builder()
                .id(workExperience.getId())
                .employee(workExperience.getEmployee())
                .companyName(workExperience.getCompanyName())
                .employeePosition(workExperience.getEmployeePosition())
                .type(workExperience.getType())
                .location(workExperience.getLocation())
                .department(workExperience.getDepartment())
                .initialPeriod(workExperience.getInitialPeriod())
                .finalPeriod(workExperience.getFinalPeriod())
                .build();
    }

    @Transactional(readOnly = true)
    public Page<WorkExperienceResponse> list(ListRequest request) {
        Employee employee;

        if (Objects.nonNull(request.getEmployeeId())) {
            employee = employeeRepository.findById(request.getEmployeeId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee ID is not found"));
        } else {
            employee = null;
        }

        Specification<WorkExperience> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(employee)) {
                predicates.add(builder.equal(root.get("employee"), employee));
            }

            if (Objects.nonNull(request.getSearch())) {
                predicates.add(builder.or(
                        builder.like(root.get("companyName"), "%" + request.getSearch() + "%"),
                        builder.like(root.get("type"), "%" + request.getSearch() + "%"),
                        builder.like(root.get("location"), "%" + request.getSearch() + "%"),
                        builder.like(root.get("department"), "%" + request.getSearch() + "%")
                ));
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<WorkExperience> workExperiences = workExperienceRepository.findAll(specification, pageable);

        List<WorkExperienceResponse> workExperienceResponses = workExperiences.stream().map(this::toWorkExperienceResponse).toList();

        return new PageImpl<>(workExperienceResponses, pageable, workExperiences.getTotalElements());
    }

    @Transactional
    public SavedResponse create(CreateWorkExperienceRequest request) {
        validationService.validate(request);

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee ID is not found"));

        WorkExperience workExperience = new WorkExperience();
        workExperience.setEmployee(employee);
        workExperience.setEmployeePosition(request.getEmployeePosition());
        workExperience.setCompanyName(request.getCompanyName());
        workExperience.setType(request.getType());
        workExperience.setLocation(request.getLocation());
        workExperience.setDepartment(request.getDepartment());
        workExperience.setInitialPeriod(request.getInitialPeriod());
        workExperience.setFinalPeriod(request.getFinalPeriod());

        WorkExperience savedWorkExperience = workExperienceRepository.save(workExperience);

        return SavedResponse.builder()
                .id(savedWorkExperience.getId())
                .label(savedWorkExperience.getCompanyName())
                .build();
    }

    @Transactional
    public SavedResponse update(UpdateWorkExperienceRequest request) {
        validationService.validate(request);

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee ID is not found"));

        WorkExperience workExperience = workExperienceRepository.findById(request.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        workExperience.setEmployee(employee);
        workExperience.setEmployeePosition(request.getEmployeePosition());
        workExperience.setCompanyName(request.getCompanyName());
        workExperience.setType(request.getType());
        workExperience.setLocation(request.getLocation());
        workExperience.setDepartment(request.getDepartment());
        workExperience.setInitialPeriod(request.getInitialPeriod());
        workExperience.setFinalPeriod(request.getFinalPeriod());

        WorkExperience savedWorkExperience = workExperienceRepository.save(workExperience);

        return SavedResponse.builder()
                .id(savedWorkExperience.getId())
                .label(savedWorkExperience.getCompanyName())
                .build();
    }

    @Transactional
    public void delete(Integer id) {
        WorkExperience workExperience = workExperienceRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        workExperienceRepository.delete(workExperience);
    }
    
}
