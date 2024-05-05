package com.galuhrmdh.simpegrestapi.model.employee;

import com.galuhrmdh.simpegrestapi.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDetailResponse {

    private Employee employee;

    private List<Parent> parents;

    private List<Spouse> spouses;

    private List<Children> children;

    private List<Education> educations;

    private List<WorkExperience> workExperiences;

    private List<Contract> contracts;

    private List<WarningLetter> warningLetters;

}
