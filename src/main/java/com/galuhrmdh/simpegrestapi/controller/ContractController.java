package com.galuhrmdh.simpegrestapi.controller;

import com.galuhrmdh.simpegrestapi.entity.User;
import com.galuhrmdh.simpegrestapi.model.*;
import com.galuhrmdh.simpegrestapi.model.contract.ContractResponse;
import com.galuhrmdh.simpegrestapi.model.contract.CreateContractRequest;
import com.galuhrmdh.simpegrestapi.model.contract.UpdateContractRequest;
import com.galuhrmdh.simpegrestapi.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ContractController {

    @Autowired
    private ContractService contractService;

    @GetMapping(
            path = "/api/contracts",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<ContractResponse>> list(User user,
                                                    @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                    @RequestParam(value = "size", required = false, defaultValue = "5") Integer size
    ) {
        ListRequest request = ListRequest.builder()
                .page(page)
                .size(size)
                .build();

        Page<ContractResponse> contractResponses = contractService.list(request);
        return WebResponse.<List<ContractResponse>>builder()
                .data(contractResponses.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(contractResponses.getNumber())
                        .totalPage(contractResponses.getTotalPages())
                        .size(contractResponses.getSize())
                        .totalElements(contractResponses.getTotalElements())
                        .build())
                .build();
    }

    @PostMapping(
            path = "/api/contracts",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<SavedResponse> create(User user, @RequestBody CreateContractRequest request) {
        SavedResponse savedResponse = contractService.create(request);

        return WebResponse.<SavedResponse>builder().data(savedResponse).build();
    }

    @PutMapping(
            path = "/api/contracts/{contractId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<SavedResponse> update(User user, @RequestBody UpdateContractRequest request, @PathVariable("contractId") Integer contractId) {
        request.setId(contractId);
        SavedResponse savedResponse = contractService.update(request);

        return WebResponse.<SavedResponse>builder().data(savedResponse).build();
    }

    @DeleteMapping(
            path = "/api/contracts/{contractId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(User user, @PathVariable("contractId") Integer contractId) {
        contractService.delete(contractId);

        return WebResponse.<String>builder().data("OK").build();
    }

    @GetMapping(
            path = "/api/contracts_reminder",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<Map<String, Object>>> contractsReminder(User user) {
        List<Map<String, Object>> contractReminder = contractService.getContractsReminder();

        return WebResponse.<List<Map<String, Object>>>builder().data(contractReminder).build();
    }
}
