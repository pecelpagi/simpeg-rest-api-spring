package com.galuhrmdh.simpegrestapi.service;

import com.galuhrmdh.simpegrestapi.entity.Department;
import com.galuhrmdh.simpegrestapi.model.DepartmentResponse;
import com.galuhrmdh.simpegrestapi.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<DepartmentResponse> list() {
        List<Department> departments = departmentRepository.findAll();

        return departments.stream().map(this::toDepartmentResponse).toList();
    }

}
