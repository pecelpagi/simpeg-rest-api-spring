package com.galuhrmdh.simpegrestapi.model.employee;

import com.galuhrmdh.simpegrestapi.enums.Citizen;
import com.galuhrmdh.simpegrestapi.enums.Religion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeReligionRecap {

    private Long total;

    private Religion religion;

}
