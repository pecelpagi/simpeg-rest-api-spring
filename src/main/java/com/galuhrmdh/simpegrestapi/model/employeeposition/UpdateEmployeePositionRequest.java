package com.galuhrmdh.simpegrestapi.model.employeeposition;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateEmployeePositionRequest {

    private Integer id;

    @NotBlank
    private String name;

}
