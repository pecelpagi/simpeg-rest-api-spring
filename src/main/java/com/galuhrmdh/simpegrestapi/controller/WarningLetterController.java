package com.galuhrmdh.simpegrestapi.controller;

import com.galuhrmdh.simpegrestapi.entity.User;
import com.galuhrmdh.simpegrestapi.model.ListRequest;
import com.galuhrmdh.simpegrestapi.model.PagingResponse;
import com.galuhrmdh.simpegrestapi.model.SavedResponse;
import com.galuhrmdh.simpegrestapi.model.WebResponse;
import com.galuhrmdh.simpegrestapi.model.warningletter.CreateWarningLetterRequest;
import com.galuhrmdh.simpegrestapi.model.warningletter.UpdateWarningLetterRequest;
import com.galuhrmdh.simpegrestapi.model.warningletter.WarningLetterResponse;
import com.galuhrmdh.simpegrestapi.service.WarningLetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WarningLetterController {

    @Autowired
    private WarningLetterService warningLetterService;

    @GetMapping(
            path = "/api/warning_letters",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<WarningLetterResponse>> list(User user,
                                                         @RequestParam(value = "search", required = false) String search,
                                                         @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                         @RequestParam(value = "size", required = false, defaultValue = "5") Integer size
    ) {
        ListRequest request = ListRequest.builder()
                .search(search)
                .page(page)
                .size(size)
                .build();

        Page<WarningLetterResponse> warningLetterResponses = warningLetterService.list(request);
        return WebResponse.<List<WarningLetterResponse>>builder()
                .data(warningLetterResponses.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(warningLetterResponses.getNumber())
                        .totalPage(warningLetterResponses.getTotalPages())
                        .size(warningLetterResponses.getSize())
                        .build())
                .build();
    }

    @PostMapping(
            path = "/api/warning_letters",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<SavedResponse> create(User user, @RequestBody CreateWarningLetterRequest request) {
        SavedResponse savedResponse = warningLetterService.create(request);

        return WebResponse.<SavedResponse>builder().data(savedResponse).build();
    }

    @PutMapping(
            path = "/api/warning_letters/{warningLetterId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<SavedResponse> update(User user, @RequestBody UpdateWarningLetterRequest request, @PathVariable("warningLetterId") Integer warningLetterId) {
        request.setId(warningLetterId);
        SavedResponse savedResponse = warningLetterService.update(request);

        return WebResponse.<SavedResponse>builder().data(savedResponse).build();
    }

    @DeleteMapping(
            path = "/api/warning_letters/{warningLetterId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(User user, @PathVariable("warningLetterId") Integer warningLetterId) {
        warningLetterService.delete(warningLetterId);

        return WebResponse.<String>builder().data("OK").build();
    }

}
