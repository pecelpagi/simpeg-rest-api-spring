package com.galuhrmdh.simpegrestapi.controller;

import com.galuhrmdh.simpegrestapi.entity.User;
import com.galuhrmdh.simpegrestapi.model.ListRequest;
import com.galuhrmdh.simpegrestapi.model.PagingResponse;
import com.galuhrmdh.simpegrestapi.model.SavedResponse;
import com.galuhrmdh.simpegrestapi.model.WebResponse;
import com.galuhrmdh.simpegrestapi.model.children.ChildrenResponse;
import com.galuhrmdh.simpegrestapi.model.children.CreateChildrenRequest;
import com.galuhrmdh.simpegrestapi.model.children.UpdateChildrenRequest;
import com.galuhrmdh.simpegrestapi.service.ChildrenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChildrenController {

    @Autowired
    private ChildrenService childrenService;

    @GetMapping(
            path = "/api/children",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<ChildrenResponse>> list(User user,
                                                    @RequestParam(value = "search", required = false) String search,
                                                    @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                    @RequestParam(value = "size", required = false, defaultValue = "5") Integer size
    ) {
        ListRequest request = ListRequest.builder()
                .search(search)
                .page(page)
                .size(size)
                .build();

        Page<ChildrenResponse> childrenResponses = childrenService.list(request);
        return WebResponse.<List<ChildrenResponse>>builder()
                .data(childrenResponses.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(childrenResponses.getNumber())
                        .totalPage(childrenResponses.getTotalPages())
                        .size(childrenResponses.getSize())
                        .totalElements(childrenResponses.getTotalElements())
                        .build())
                .build();
    }

    @PostMapping(
            path = "/api/children",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<SavedResponse> create(User user, @RequestBody CreateChildrenRequest request) {
        SavedResponse savedResponse = childrenService.create(request);

        return WebResponse.<SavedResponse>builder().data(savedResponse).build();
    }

    @PutMapping(
            path = "/api/children/{childId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<SavedResponse> update(User user, @RequestBody UpdateChildrenRequest request, @PathVariable("childId") Integer childId) {
        request.setId(childId);
        SavedResponse savedResponse = childrenService.update(request);

        return WebResponse.<SavedResponse>builder().data(savedResponse).build();
    }

    @DeleteMapping(
            path = "/api/children/{childId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(User user, @PathVariable("childId") Integer childId) {
        childrenService.delete(childId);

        return WebResponse.<String>builder().data("OK").build();
    }

}
