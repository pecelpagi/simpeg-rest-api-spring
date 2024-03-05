package com.galuhrmdh.simpegrestapi.model.workexperience;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateWorkExperienceRequest {

    private Integer employeeId;

    private String companyName;

    private String type;

    private String location;

    private String department;

    private Integer employeePositionId;

    private Date initialPeriod;

    private Date finalPeriod;
}
