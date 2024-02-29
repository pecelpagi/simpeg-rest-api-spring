package com.galuhrmdh.simpegrestapi.controller;

import com.galuhrmdh.simpegrestapi.entity.User;
import com.galuhrmdh.simpegrestapi.model.CreateDepartmentRequest;
import com.galuhrmdh.simpegrestapi.model.SavedResponse;
import com.galuhrmdh.simpegrestapi.model.UpdateDepartmentRequest;
import com.galuhrmdh.simpegrestapi.model.WebResponse;
import com.galuhrmdh.simpegrestapi.model.contract.CreateContractRequest;
import com.galuhrmdh.simpegrestapi.model.contract.UpdateContractRequest;
import com.galuhrmdh.simpegrestapi.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class ContractController {

    @Autowired
    private ContractService contractService;

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
}
