package com.galuhrmdh.simpegrestapi.model.contract;

import com.galuhrmdh.simpegrestapi.enums.Citizen;
import com.galuhrmdh.simpegrestapi.enums.ContractStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateContractRequest {

    private Integer employeeId;

    private ContractStatus contractStatus;

    private Date startDate;

    private Integer contractLengthMonth;
}
