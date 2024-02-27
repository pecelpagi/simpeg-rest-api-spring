package com.galuhrmdh.simpegrestapi.model;

import com.galuhrmdh.simpegrestapi.enums.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {

    @NotBlank
    @Size(max = 16)
    private String idNumber;

    @NotBlank
    @Size(max = 100)
    private String name;

    private Gender gender;

    @NotBlank
    @Size(max = 4)
    private String departmentCode;

    private Date entryDate;

    @NotBlank
    @Size(max = 255)
    private String address;

    @NotBlank
    @Size(max = 100)
    private String city;

    @NotBlank
    @Size(max = 100)
    private String originCity;

    @NotBlank
    @Size(max = 100)
    private String birthplace;

    private Date birthdate;

    private Integer employeePositionId;

    private Religion religion;

    private Citizen citizen;

    private MaritalStatus maritalStatus;

    private IncomeTaxStatus incomeTaxStatus;

    private BloodType bloodType;

    @Size(max = 16)
    private String bpjsHealth;

    @Size(max = 16)
    private String bpjsEmployment;

    @Size(max = 16)
    private String bpjsRetirement;

    @Size(max = 100)
    private String profilePicture;

}
