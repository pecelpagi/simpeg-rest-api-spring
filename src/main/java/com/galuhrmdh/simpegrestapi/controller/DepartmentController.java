package com.galuhrmdh.simpegrestapi.controller;

import com.galuhrmdh.simpegrestapi.entity.User;
import com.galuhrmdh.simpegrestapi.model.DepartmentResponse;
import com.galuhrmdh.simpegrestapi.model.WebResponse;
import com.galuhrmdh.simpegrestapi.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping(
            path = "/api/departments",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<DepartmentResponse>> list(User user) {
        List<DepartmentResponse> departmentResponses = departmentService.list();
        return WebResponse.<List<DepartmentResponse>>builder().data(departmentResponses).build();
    }

}
