package com.galuhrmdh.simpegrestapi.model.warningletter;

import com.galuhrmdh.simpegrestapi.enums.Regarding;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateWarningLetterRequest {

    private Integer id;

    private Integer employeeId;

    private Date dateFacingHrd;

    private Date violationDate;

    private Regarding regarding;

    private String violation1;

    private String violation2;

    private Date suspensionPeriod;

    private String attachment;

}
