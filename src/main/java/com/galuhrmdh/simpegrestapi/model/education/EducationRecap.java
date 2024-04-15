package com.galuhrmdh.simpegrestapi.model.education;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EducationRecap {

    private Long total;

    private String educationLevel;

}
