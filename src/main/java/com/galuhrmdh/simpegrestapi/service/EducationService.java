package com.galuhrmdh.simpegrestapi.service;

import com.galuhrmdh.simpegrestapi.entity.Children;
import com.galuhrmdh.simpegrestapi.entity.Education;
import com.galuhrmdh.simpegrestapi.entity.Employee;
import com.galuhrmdh.simpegrestapi.model.ListRequest;
import com.galuhrmdh.simpegrestapi.model.SavedResponse;
import com.galuhrmdh.simpegrestapi.model.education.CreateEducationRequest;
import com.galuhrmdh.simpegrestapi.model.education.EducationRecap;
import com.galuhrmdh.simpegrestapi.model.education.EducationResponse;
import com.galuhrmdh.simpegrestapi.model.education.UpdateEducationRequest;
import com.galuhrmdh.simpegrestapi.model.employeeposition.EmployeePositionRecap;
import com.galuhrmdh.simpegrestapi.repository.EducationRepository;
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
import java.util.Objects;

@Service
public class EducationService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private ValidationService validationService;

    private EducationResponse toEducationResponse(Education education) {
        return EducationResponse.builder()
                .id(education.getId())
                .employee(education.getEmployee())
                .educationLevel(education.getEducationLevel())
                .major(education.getMajor())
                .name(education.getName())
                .location(education.getLocation())
                .graduationYear(education.getGraduationYear())
                .certificateNumber(education.getCertificateNumber())
                .build();
    }

    @Transactional(readOnly = true)
    public Page<EducationResponse> list(ListRequest request) {
        Specification<Education> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(request.getSearch())) {
                predicates.add(builder.or(
                        builder.like(root.get("educationLevel"), "%" + request.getSearch() + "%"),
                        builder.like(root.get("name"), "%" + request.getSearch() + "%"),
                        builder.like(root.get("major"), "%" + request.getSearch() + "%"),
                        builder.like(root.get("location"), "%" + request.getSearch() + "%"),
                        builder.like(root.get("certificateNumber"), "%" + request.getSearch() + "%")
                ));
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Education> educations = educationRepository.findAll(specification, pageable);

        List<EducationResponse> educationResponses = educations.stream().map(this::toEducationResponse).toList();

        return new PageImpl<>(educationResponses, pageable, educations.getTotalElements());
    }

    @Transactional
    public SavedResponse create(CreateEducationRequest request) {
        validationService.validate(request);

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee ID is not found"));

        Education education = new Education();
        education.setEmployee(employee);
        education.setEducationLevel(request.getEducationLevel());
        education.setMajor(request.getMajor());
        education.setName(request.getName());
        education.setLocation(request.getLocation());
        education.setGraduationYear(request.getGraduationYear());
        education.setCertificateNumber(request.getCertificateNumber());

        Education savedEducation = educationRepository.save(education);

        return SavedResponse.builder()
                .id(savedEducation.getId())
                .label(savedEducation.getCertificateNumber())
                .build();
    }

    @Transactional
    public SavedResponse update(UpdateEducationRequest request) {
        validationService.validate(request);

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee ID is not found"));

        Education education = educationRepository.findById(request.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        education.setEmployee(employee);
        education.setEducationLevel(request.getEducationLevel());
        education.setMajor(request.getMajor());
        education.setName(request.getName());
        education.setLocation(request.getLocation());
        education.setGraduationYear(request.getGraduationYear());
        education.setCertificateNumber(request.getCertificateNumber());

        Education savedEducation = educationRepository.save(education);

        return SavedResponse.builder()
                .id(savedEducation.getId())
                .label(savedEducation.getCertificateNumber())
                .build();
    }

    @Transactional
    public void delete(Integer id) {
        Education education = educationRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        educationRepository.delete(education);
    }

    @Transactional
    public List<EducationRecap> getEducationRecap() {
        return educationRepository.getEducationRecap();
    }

}
