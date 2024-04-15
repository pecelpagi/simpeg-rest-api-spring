package com.galuhrmdh.simpegrestapi.model.employee;

import com.galuhrmdh.simpegrestapi.enums.Citizen;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeCitizenRecap {

    private Long total;

    private Citizen citizen;

}
