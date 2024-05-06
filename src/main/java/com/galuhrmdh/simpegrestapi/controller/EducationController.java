package com.galuhrmdh.simpegrestapi.controller;

import com.galuhrmdh.simpegrestapi.entity.User;
import com.galuhrmdh.simpegrestapi.model.ListRequest;
import com.galuhrmdh.simpegrestapi.model.PagingResponse;
import com.galuhrmdh.simpegrestapi.model.SavedResponse;
import com.galuhrmdh.simpegrestapi.model.WebResponse;
import com.galuhrmdh.simpegrestapi.model.education.CreateEducationRequest;
import com.galuhrmdh.simpegrestapi.model.education.EducationRecap;
import com.galuhrmdh.simpegrestapi.model.education.EducationResponse;
import com.galuhrmdh.simpegrestapi.model.education.UpdateEducationRequest;
import com.galuhrmdh.simpegrestapi.model.employeeposition.EmployeePositionRecap;
import com.galuhrmdh.simpegrestapi.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EducationController {

    @Autowired
    private EducationService educationService;

    @GetMapping(
            path = "/api/educations",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<EducationResponse>> list(User user,
                                                     @RequestParam(value = "employeeId", required = false) Integer employeeId,
                                                     @RequestParam(value = "search", required = false) String search,
                                                     @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                     @RequestParam(value = "size", required = false, defaultValue = "5") Integer size
    ) {
        ListRequest request = ListRequest.builder()
                .employeeId(employeeId)
                .search(search)
                .page(page)
                .size(size)
                .build();

        Page<EducationResponse> educationResponses = educationService.list(request);
        return WebResponse.<List<EducationResponse>>builder()
                .data(educationResponses.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(educationResponses.getNumber())
                        .totalPage(educationResponses.getTotalPages())
                        .size(educationResponses.getSize())
                        .totalElements(educationResponses.getTotalElements())
                        .build())
                .build();
    }

    @PostMapping(
            path = "/api/educations",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<SavedResponse> create(User user, @RequestBody CreateEducationRequest request) {
        SavedResponse savedResponse = educationService.create(request);

        return WebResponse.<SavedResponse>builder().data(savedResponse).build();
    }

    @PutMapping(
            path = "/api/educations/{educationId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<SavedResponse> update(User user, @RequestBody UpdateEducationRequest request, @PathVariable("educationId") Integer educationId) {
        request.setId(educationId);
        SavedResponse savedResponse = educationService.update(request);

        return WebResponse.<SavedResponse>builder().data(savedResponse).build();
    }

    @DeleteMapping(
            path = "/api/educations/{educationId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(User user, @PathVariable("educationId") Integer educationId) {
        educationService.delete(educationId);

        return WebResponse.<String>builder().data("OK").build();
    }

    @GetMapping(
            path = "/api/educations_recap",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<EducationRecap>> list(User user) {
        List<EducationRecap> educationRecap = educationService.getEducationRecap();
        return WebResponse.<List<EducationRecap>>builder()
                .data(educationRecap)
                .build();
    }
}
