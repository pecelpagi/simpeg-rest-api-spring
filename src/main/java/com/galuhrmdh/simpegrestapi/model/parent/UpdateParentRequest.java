package com.galuhrmdh.simpegrestapi.model.parent;

import com.galuhrmdh.simpegrestapi.enums.ChildStatus;
import com.galuhrmdh.simpegrestapi.enums.Gender;
import com.galuhrmdh.simpegrestapi.enums.ParentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateParentRequest {

    private Integer id;

    private Integer employeeId;

    private String idNumber;

    private String name;

    private String birthplace;

    private Date birthdate;

    private Gender gender;

    private String lastEducation;

    private String occupation;

    private ParentStatus parentStatus;
    
}
