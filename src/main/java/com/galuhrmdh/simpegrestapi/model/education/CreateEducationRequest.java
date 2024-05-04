package com.galuhrmdh.simpegrestapi.model.education;

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
public class CreateEducationRequest {

    private Integer employeeId;

    private String educationLevel;

    private String major;

    private String name;

    private String location;

    private String graduationYear;

    private String certificateNumber;

    private String attachment;
}
