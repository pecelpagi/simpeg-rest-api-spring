package com.galuhrmdh.simpegrestapi.model.contract;

import com.galuhrmdh.simpegrestapi.entity.Employee;
import com.galuhrmdh.simpegrestapi.enums.ContractStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractResponse {

    private Integer id;

    private Employee employee;

    private ContractStatus contractStatus;

    private Date startDate;

    private Integer contractLengthMonth;

}
