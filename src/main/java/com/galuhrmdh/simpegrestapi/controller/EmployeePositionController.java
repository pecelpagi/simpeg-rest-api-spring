package com.galuhrmdh.simpegrestapi.controller;

import com.galuhrmdh.simpegrestapi.entity.User;
import com.galuhrmdh.simpegrestapi.model.*;
import com.galuhrmdh.simpegrestapi.model.employeeposition.CreateEmployeePositionRequest;
import com.galuhrmdh.simpegrestapi.model.employeeposition.EmployeePositionResponse;
import com.galuhrmdh.simpegrestapi.model.employeeposition.UpdateEmployeePositionRequest;
import com.galuhrmdh.simpegrestapi.service.EmployeePositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeePositionController {

    @Autowired
    private EmployeePositionService employeePositionService;

    @GetMapping(
            path = "/api/employee_positions",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<EmployeePositionResponse>> list(User user,
                                                            @RequestParam(value = "search", required = false) String search,
                                                            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                            @RequestParam(value = "size", required = false, defaultValue = "5") Integer size
    ) {
        ListRequest request = ListRequest.builder()
                .search(search)
                .page(page)
                .size(size)
                .build();

        Page<EmployeePositionResponse> employeePositionResponses = employeePositionService.list(request);
        return WebResponse.<List<EmployeePositionResponse>>builder()
                .data(employeePositionResponses.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(employeePositionResponses.getNumber())
                        .totalPage(employeePositionResponses.getTotalPages())
                        .totalElements(employeePositionResponses.getTotalElements())
                        .size(employeePositionResponses.getSize())
                        .build())
                .build();
    }

    @PostMapping(
            path = "/api/employee_positions",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<SavedResponse> create(User user, @RequestBody CreateEmployeePositionRequest request) {
        SavedResponse savedResponse = employeePositionService.create(request);

        return WebResponse.<SavedResponse>builder().data(savedResponse).build();
    }

    @PutMapping(
            path = "/api/employee_positions/{employeePositionId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<SavedResponse> update(User user, @RequestBody UpdateEmployeePositionRequest request, @PathVariable("employeePositionId") Integer employeePositionId) {
        request.setId(employeePositionId);
        SavedResponse savedResponse = employeePositionService.update(request);

        return WebResponse.<SavedResponse>builder().data(savedResponse).build();
    }

    @DeleteMapping(
            path = "/api/employee_positions/{employeePositionId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(User user, @PathVariable("employeePositionId") Integer employeePositionId) {
        employeePositionService.delete(employeePositionId);

        return WebResponse.<String>builder().data("OK").build();
    }

}
