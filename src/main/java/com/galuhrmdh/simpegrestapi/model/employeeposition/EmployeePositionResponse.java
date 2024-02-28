package com.galuhrmdh.simpegrestapi.model.employeeposition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeePositionResponse {

    private Integer id;

    private String name;

}
