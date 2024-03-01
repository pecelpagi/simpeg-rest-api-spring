package com.galuhrmdh.simpegrestapi.model.spouse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateSpouseRequest {

    private Integer id;

    private Integer employeeId;

    private String idNumber;

    private String name;

    private String birthplace;

    private Date birthdate;

    private Date dateOfMarriage;

    private Integer spouseSequence;

    private String lastEducation;

    private String occupation;
    
}
