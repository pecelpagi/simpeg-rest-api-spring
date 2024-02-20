package com.galuhrmdh.simpegrestapi.controller;

import com.galuhrmdh.simpegrestapi.entity.User;
import com.galuhrmdh.simpegrestapi.model.DepartmentRequest;
import com.galuhrmdh.simpegrestapi.model.DepartmentResponse;
import com.galuhrmdh.simpegrestapi.model.PagingResponse;
import com.galuhrmdh.simpegrestapi.model.WebResponse;
import com.galuhrmdh.simpegrestapi.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public WebResponse<List<DepartmentResponse>> list(User user,
                                                      @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                      @RequestParam(value = "size", required = false, defaultValue = "5") Integer size
    ) {
        DepartmentRequest request = DepartmentRequest.builder()
                .page(page)
                .size(size)
                .build();

        Page<DepartmentResponse> departmentResponses = departmentService.list(request);
        return WebResponse.<List<DepartmentResponse>>builder()
                .data(departmentResponses.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(departmentResponses.getNumber())
                        .totalPage(departmentResponses.getTotalPages())
                        .size(departmentResponses.getSize())
                        .build())
                .build();
    }

}
