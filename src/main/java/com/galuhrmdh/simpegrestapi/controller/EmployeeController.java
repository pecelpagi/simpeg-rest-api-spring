package com.galuhrmdh.simpegrestapi.controller;

import com.galuhrmdh.simpegrestapi.entity.User;
import com.galuhrmdh.simpegrestapi.model.ListRequest;
import com.galuhrmdh.simpegrestapi.model.PagingResponse;
import com.galuhrmdh.simpegrestapi.model.SavedResponse;
import com.galuhrmdh.simpegrestapi.model.WebResponse;
import com.galuhrmdh.simpegrestapi.model.employee.CreateEmployeeRequest;
import com.galuhrmdh.simpegrestapi.model.employee.EmployeeResponse;
import com.galuhrmdh.simpegrestapi.model.employee.UpdateEmployeeRequest;
import com.galuhrmdh.simpegrestapi.model.employeeposition.EmployeePositionResponse;
import com.galuhrmdh.simpegrestapi.model.employeeposition.UpdateEmployeePositionRequest;
import com.galuhrmdh.simpegrestapi.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @GetMapping(
            path = "/api/employees",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<EmployeeResponse>> list(User user,
                                                    @RequestParam(value = "search", required = false) String search,
                                                    @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                    @RequestParam(value = "size", required = false, defaultValue = "5") Integer size
    ) {
        ListRequest request = ListRequest.builder()
                .search(search)
                .page(page)
                .size(size)
                .build();

        Page<EmployeeResponse> employeeResponses = employeeService.list(request);
        return WebResponse.<List<EmployeeResponse>>builder()
                .data(employeeResponses.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(employeeResponses.getNumber())
                        .totalPage(employeeResponses.getTotalPages())
                        .size(employeeResponses.getSize())
                        .totalElements(employeeResponses.getTotalElements())
                        .build())
                .build();
    }

    @PostMapping(
            path = "/api/employees",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public SavedResponse create(User user, @RequestBody CreateEmployeeRequest request) {
        return employeeService.create(request);
    }

    @PutMapping(
            path = "/api/employees/{employeeId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<SavedResponse> update(User user, @RequestBody UpdateEmployeeRequest request, @PathVariable("employeeId") Integer employeeId) {
        request.setId(employeeId);
        SavedResponse savedResponse = employeeService.update(request);

        return WebResponse.<SavedResponse>builder().data(savedResponse).build();
    }

    @DeleteMapping(
            path = "/api/employees/{employeeId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(User user, @PathVariable("employeeId") Integer employeeId) {
        employeeService.delete(employeeId);

        return WebResponse.<String>builder().data("OK").build();
    }

}
