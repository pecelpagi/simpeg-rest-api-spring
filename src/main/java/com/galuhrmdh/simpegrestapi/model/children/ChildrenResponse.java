package com.galuhrmdh.simpegrestapi.model.children;

import com.galuhrmdh.simpegrestapi.entity.Employee;
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
public class ChildrenResponse {

    private Integer id;

    private Employee employee;

    private String idNumber;

    private String name;

    private String birthplace;

    private Date birthdate;

    private Gender gender;

    private Integer childSequence;

    private String lastEducation;

    private String occupation;

    private ChildStatus childStatus;
}
