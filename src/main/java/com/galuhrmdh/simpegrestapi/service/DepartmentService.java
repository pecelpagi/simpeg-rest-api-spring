package com.galuhrmdh.simpegrestapi.service;

import com.galuhrmdh.simpegrestapi.entity.Department;
import com.galuhrmdh.simpegrestapi.model.*;
import com.galuhrmdh.simpegrestapi.model.department.DepartmentRecap;
import com.galuhrmdh.simpegrestapi.model.employeeposition.EmployeePositionRecap;
import com.galuhrmdh.simpegrestapi.repository.DepartmentRepository;
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
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ValidationService validationService;

    private DepartmentResponse toDepartmentResponse(Department department) {
        return DepartmentResponse.builder()
                .id(department.getId())
                .code(department.getCode())
                .name(department.getName())
                .build();
    }

    @Transactional(readOnly = true)
    public Page<DepartmentResponse> list(ListRequest request) {
        Specification<Department> specification = (root, query, builder) -> {
          List<Predicate> predicates = new ArrayList<>();

          if (Objects.nonNull(request.getSearch())) {
              predicates.add(builder.or(
                      builder.like(root.get("code"), "%" + request.getSearch() + "%"),
                      builder.like(root.get("name"), "%" + request.getSearch() + "%")
              ));
          }

          return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Department> departments = departmentRepository.findAll(specification, pageable);

        List<DepartmentResponse> departmentResponses = departments.stream().map(this::toDepartmentResponse).toList();

        return new PageImpl<>(departmentResponses, pageable, departments.getTotalElements());
    }

    @Transactional
    public SavedResponse create(CreateDepartmentRequest request) {
        validationService.validate(request);

        Department department = new Department();
        department.setCode(request.getCode());
        department.setName(request.getName());

        department = departmentRepository.save(department);

        return SavedResponse.builder()
                .id(department.getId())
                .label(department.getName())
                .build();
    }

    @Transactional
    public SavedResponse update(UpdateDepartmentRequest request) {
        validationService.validate(request);

        Department department = departmentRepository.findById(request.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        department.setCode(request.getCode());
        department.setName(request.getName());

        department = departmentRepository.save(department);

        return SavedResponse.builder()
                .id(department.getId())
                .label(department.getName())
                .build();
    }

    @Transactional
    public void delete(Integer id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        departmentRepository.delete(department);
    }

    public List<DepartmentRecap> getDepartmentRecap() {
        return departmentRepository.getDepartmentRecap();
    }
}
