package com.galuhrmdh.simpegrestapi.model.spouse;

import com.galuhrmdh.simpegrestapi.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpouseResponse {

    private Integer id;

    private Employee employee;

    private String idNumber;

    private String name;

    private String birthplace;

    private Date birthdate;

    private Date dateOfMarriage;

    private Integer spouseSequence;

    private String lastEducation;

    private String occupation;

}
