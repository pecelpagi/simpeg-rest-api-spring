package com.galuhrmdh.simpegrestapi.model.employee;

import com.galuhrmdh.simpegrestapi.entity.Department;
import com.galuhrmdh.simpegrestapi.entity.EmployeePosition;
import com.galuhrmdh.simpegrestapi.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeResponse {

    private Integer id;

    private String idNumber;

    private String name;

    private Gender gender;

    private Department department;

    private Date entryDate;

    private String address;

    private String city;

    private String originCity;

    private String birthplace;

    private Date birthdate;

    private EmployeePosition employeePosition;

    private Religion religion;

    private Citizen citizen;

    private MaritalStatus maritalStatus;

    private IncomeTaxStatus incomeTaxStatus;

    private BloodType bloodType;

    private String bpjsHealth;

    private String bpjsEmployment;

    private String bpjsRetirement;

    private String profilePicture;

}
