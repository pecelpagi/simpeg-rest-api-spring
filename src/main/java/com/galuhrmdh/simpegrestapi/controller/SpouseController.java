package com.galuhrmdh.simpegrestapi.controller;

import com.galuhrmdh.simpegrestapi.entity.User;
import com.galuhrmdh.simpegrestapi.model.ListRequest;
import com.galuhrmdh.simpegrestapi.model.PagingResponse;
import com.galuhrmdh.simpegrestapi.model.SavedResponse;
import com.galuhrmdh.simpegrestapi.model.WebResponse;
import com.galuhrmdh.simpegrestapi.model.spouse.CreateSpouseRequest;
import com.galuhrmdh.simpegrestapi.model.spouse.SpouseResponse;
import com.galuhrmdh.simpegrestapi.model.spouse.UpdateSpouseRequest;
import com.galuhrmdh.simpegrestapi.service.SpouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SpouseController {

    @Autowired
    private SpouseService spouseService;

    @GetMapping(
            path = "/api/spouses",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<SpouseResponse>> list(User user,
                                                            @RequestParam(value = "search", required = false) String search,
                                                            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                            @RequestParam(value = "size", required = false, defaultValue = "5") Integer size
    ) {
        ListRequest request = ListRequest.builder()
                .search(search)
                .page(page)
                .size(size)
                .build();

        Page<SpouseResponse> spouseResponses = spouseService.list(request);
        return WebResponse.<List<SpouseResponse>>builder()
                .data(spouseResponses.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(spouseResponses.getNumber())
                        .totalPage(spouseResponses.getTotalPages())
                        .size(spouseResponses.getSize())
                        .totalElements(spouseResponses.getTotalElements())
                        .build())
                .build();
    }

    @PostMapping(
            path = "/api/spouses",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<SavedResponse> create(User user, @RequestBody CreateSpouseRequest request) {
        SavedResponse savedResponse = spouseService.create(request);

        return WebResponse.<SavedResponse>builder().data(savedResponse).build();
    }

    @PutMapping(
            path = "/api/spouses/{spouseId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<SavedResponse> update(User user, @RequestBody UpdateSpouseRequest request, @PathVariable("spouseId") Integer spouseId) {
        request.setId(spouseId);
        SavedResponse savedResponse = spouseService.update(request);

        return WebResponse.<SavedResponse>builder().data(savedResponse).build();
    }

    @DeleteMapping(
            path = "/api/spouses/{spouseId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(User user, @PathVariable("spouseId") Integer spouseId) {
        spouseService.delete(spouseId);

        return WebResponse.<String>builder().data("OK").build();
    }
    
}
