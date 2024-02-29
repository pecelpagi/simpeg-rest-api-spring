package com.galuhrmdh.simpegrestapi.model.contract;

import com.galuhrmdh.simpegrestapi.enums.ContractStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateContractRequest {

    private Integer id;

    private Integer employeeId;

    private ContractStatus contractStatus;

    private Date startDate;

    private Integer contractLengthMonth;
}
