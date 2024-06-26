package com.galuhrmdh.simpegrestapi.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListRequest {

    @NotNull
    private Integer page;

    @NotNull
    private Integer size;

    private String search;

    private Integer employeeId;
}
