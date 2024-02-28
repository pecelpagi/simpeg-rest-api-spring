package com.galuhrmdh.simpegrestapi.controller;

import com.galuhrmdh.simpegrestapi.entity.User;
import com.galuhrmdh.simpegrestapi.model.*;
import com.galuhrmdh.simpegrestapi.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
                                                      @RequestParam(value = "search", required = false) String search,
                                                      @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                      @RequestParam(value = "size", required = false, defaultValue = "5") Integer size
    ) {
        ListRequest request = ListRequest.builder()
                .search(search)
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

    @PostMapping(
            path = "/api/departments",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<SavedResponse> create(User user, @RequestBody CreateDepartmentRequest request) {
        SavedResponse savedResponse = departmentService.create(request);

        return WebResponse.<SavedResponse>builder().data(savedResponse).build();
    }

    @PutMapping(
            path = "/api/departments/{departmentId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<SavedResponse> update(User user, @RequestBody UpdateDepartmentRequest request, @PathVariable("departmentId") Integer departmentId) {
        request.setId(departmentId);
        SavedResponse savedResponse = departmentService.update(request);

        return WebResponse.<SavedResponse>builder().data(savedResponse).build();
    }

    @DeleteMapping(
            path = "/api/departments/{departmentId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(User user, @PathVariable("departmentId") Integer departmentId) {
        departmentService.delete(departmentId);

        return WebResponse.<String>builder().data("OK").build();
    }
}
