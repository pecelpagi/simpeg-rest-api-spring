package com.galuhrmdh.simpegrestapi.controller;

import com.galuhrmdh.simpegrestapi.entity.User;
import com.galuhrmdh.simpegrestapi.model.EmployeeRequest;
import com.galuhrmdh.simpegrestapi.model.SavedResponse;
import com.galuhrmdh.simpegrestapi.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping(
            path = "/api/employees"
    )
    public SavedResponse create(User user, @RequestBody EmployeeRequest request) {
        return employeeService.create(request);
    }

}
