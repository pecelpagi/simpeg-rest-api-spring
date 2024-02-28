package com.galuhrmdh.simpegrestapi.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateDepartmentRequest {

    @NotBlank
    private String code;

    @NotBlank
    private String name;

}
