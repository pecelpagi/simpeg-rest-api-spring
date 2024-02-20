package com.galuhrmdh.simpegrestapi.service;

import com.galuhrmdh.simpegrestapi.entity.Department;
import com.galuhrmdh.simpegrestapi.model.DepartmentRequest;
import com.galuhrmdh.simpegrestapi.model.DepartmentResponse;
import com.galuhrmdh.simpegrestapi.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    private DepartmentResponse toDepartmentResponse(Department department) {
        return DepartmentResponse.builder()
                .code(department.getCode())
                .name(department.getName())
                .build();
    }

    @Transactional(readOnly = true)
    public Page<DepartmentResponse> list(DepartmentRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Department> departments = departmentRepository.findAll(pageable);

        List<DepartmentResponse> departmentResponses = departments.stream().map(this::toDepartmentResponse).toList();

        return new PageImpl<>(departmentResponses, pageable, departments.getTotalElements());
    }

}
