package com.galuhrmdh.simpegrestapi.controller;

import com.galuhrmdh.simpegrestapi.entity.User;
import com.galuhrmdh.simpegrestapi.model.ListRequest;
import com.galuhrmdh.simpegrestapi.model.PagingResponse;
import com.galuhrmdh.simpegrestapi.model.SavedResponse;
import com.galuhrmdh.simpegrestapi.model.WebResponse;
import com.galuhrmdh.simpegrestapi.model.parent.CreateParentRequest;
import com.galuhrmdh.simpegrestapi.model.parent.ParentResponse;
import com.galuhrmdh.simpegrestapi.model.parent.UpdateParentRequest;
import com.galuhrmdh.simpegrestapi.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ParentController {

    @Autowired
    private ParentService parentService;

    @GetMapping(
            path = "/api/parents",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<ParentResponse>> list(User user,
                                                    @RequestParam(value = "search", required = false) String search,
                                                    @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                    @RequestParam(value = "size", required = false, defaultValue = "5") Integer size
    ) {
        ListRequest request = ListRequest.builder()
                .search(search)
                .page(page)
                .size(size)
                .build();

        Page<ParentResponse> parentResponses = parentService.list(request);
        return WebResponse.<List<ParentResponse>>builder()
                .data(parentResponses.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(parentResponses.getNumber())
                        .totalPage(parentResponses.getTotalPages())
                        .size(parentResponses.getSize())
                        .build())
                .build();
    }

    @PostMapping(
            path = "/api/parents",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<SavedResponse> create(User user, @RequestBody CreateParentRequest request) {
        SavedResponse savedResponse = parentService.create(request);

        return WebResponse.<SavedResponse>builder().data(savedResponse).build();
    }

    @PutMapping(
            path = "/api/parents/{parentId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<SavedResponse> update(User user, @RequestBody UpdateParentRequest request, @PathVariable("parentId") Integer parentId) {
        request.setId(parentId);
        SavedResponse savedResponse = parentService.update(request);

        return WebResponse.<SavedResponse>builder().data(savedResponse).build();
    }

    @DeleteMapping(
            path = "/api/parents/{parentId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(User user, @PathVariable("parentId") Integer parentId) {
        parentService.delete(parentId);

        return WebResponse.<String>builder().data("OK").build();
    }

}
