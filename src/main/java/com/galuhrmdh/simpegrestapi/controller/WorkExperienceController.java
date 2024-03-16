package com.galuhrmdh.simpegrestapi.controller;

import com.galuhrmdh.simpegrestapi.entity.User;
import com.galuhrmdh.simpegrestapi.model.ListRequest;
import com.galuhrmdh.simpegrestapi.model.PagingResponse;
import com.galuhrmdh.simpegrestapi.model.SavedResponse;
import com.galuhrmdh.simpegrestapi.model.WebResponse;
import com.galuhrmdh.simpegrestapi.model.workexperience.CreateWorkExperienceRequest;
import com.galuhrmdh.simpegrestapi.model.workexperience.UpdateWorkExperienceRequest;
import com.galuhrmdh.simpegrestapi.model.workexperience.WorkExperienceResponse;
import com.galuhrmdh.simpegrestapi.service.WorkExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WorkExperienceController {

    @Autowired
    private WorkExperienceService workExperienceService;

    @GetMapping(
            path = "/api/work_experiences",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<WorkExperienceResponse>> list(User user,
                                                          @RequestParam(value = "search", required = false) String search,
                                                          @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                          @RequestParam(value = "size", required = false, defaultValue = "5") Integer size
    ) {
        ListRequest request = ListRequest.builder()
                .search(search)
                .page(page)
                .size(size)
                .build();

        Page<WorkExperienceResponse> workExperienceResponses = workExperienceService.list(request);
        return WebResponse.<List<WorkExperienceResponse>>builder()
                .data(workExperienceResponses.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(workExperienceResponses.getNumber())
                        .totalPage(workExperienceResponses.getTotalPages())
                        .size(workExperienceResponses.getSize())
                        .totalElements(workExperienceResponses.getTotalElements())
                        .build())
                .build();
    }

    @PostMapping(
            path = "/api/work_experiences",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<SavedResponse> create(User user, @RequestBody CreateWorkExperienceRequest request) {
        SavedResponse savedResponse = workExperienceService.create(request);

        return WebResponse.<SavedResponse>builder().data(savedResponse).build();
    }

    @PutMapping(
            path = "/api/work_experiences/{workExperienceId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<SavedResponse> update(User user, @RequestBody UpdateWorkExperienceRequest request, @PathVariable("workExperienceId") Integer workExperienceId) {
        request.setId(workExperienceId);
        SavedResponse savedResponse = workExperienceService.update(request);

        return WebResponse.<SavedResponse>builder().data(savedResponse).build();
    }

    @DeleteMapping(
            path = "/api/work_experiences/{workExperienceId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(User user, @PathVariable("workExperienceId") Integer workExperienceId) {
        workExperienceService.delete(workExperienceId);

        return WebResponse.<String>builder().data("OK").build();
    }
    
}
