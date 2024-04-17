package com.galuhrmdh.simpegrestapi.model.workexperience;

import com.galuhrmdh.simpegrestapi.entity.Employee;
import com.galuhrmdh.simpegrestapi.entity.EmployeePosition;
import com.galuhrmdh.simpegrestapi.enums.ChildStatus;
import com.galuhrmdh.simpegrestapi.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkExperienceResponse {

    private Integer id;

    private Employee employee;

    private String companyName;

    private String type;

    private String location;

    private String department;

    private String employeePosition;

    private Date initialPeriod;

    private Date finalPeriod;
}
