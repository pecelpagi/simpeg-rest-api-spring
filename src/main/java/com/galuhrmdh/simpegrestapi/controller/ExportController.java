package com.galuhrmdh.simpegrestapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.galuhrmdh.simpegrestapi.entity.User;
import com.galuhrmdh.simpegrestapi.model.WebResponse;
import com.galuhrmdh.simpegrestapi.service.ExportService;
import com.galuhrmdh.simpegrestapi.service.ParentService;
import com.galuhrmdh.simpegrestapi.service.RabbitPublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExportController {

    @Autowired
    private RabbitPublisherService rabbitPublisherService;

    @Autowired
    private ExportService exportService;

    @PostMapping(
            path = "/api/export-employee",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> exportEmployee(User user) throws JsonProcessingException {
        boolean isExporting = exportService.getExportEmployeeStatus().equals("IS_EXPORTING");

        if (isExporting) {
            return WebResponse.<String>builder().data("EXPORTING").build();
        }

        rabbitPublisherService.exportEmployee();

        return WebResponse.<String>builder().data("OK").build();
    }

    @PostMapping(
            path = "/api/export-warning-report",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> exportWarningReport(User user) throws JsonProcessingException {
        rabbitPublisherService.exportWarningReport();

        return WebResponse.<String>builder().data("OK").build();
    }

    @GetMapping(
            path = "/api/export-employee-status",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> exportEmployeeStatus(User user) throws JsonProcessingException {
        return WebResponse.<String>builder().data(exportService.getExportEmployeeStatus()).build();
    }

}
