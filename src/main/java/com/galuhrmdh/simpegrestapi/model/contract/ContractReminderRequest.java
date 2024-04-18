package com.galuhrmdh.simpegrestapi.model.contract;

import lombok.Data;

import java.util.Date;

@Data
public class ContractReminderRequest {

    private Date startDate;

    private Date endDate;

}
