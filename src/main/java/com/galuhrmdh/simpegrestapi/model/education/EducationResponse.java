package com.galuhrmdh.simpegrestapi.model.education;

import com.galuhrmdh.simpegrestapi.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EducationResponse {

    private Integer id;

    private Employee employee;

    private String educationLevel;

    private String major;

    private String name;

    private String location;

    private String graduationYear;

    private String certificateNumber;

}
