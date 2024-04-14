package com.galuhrmdh.simpegrestapi.model;

import com.galuhrmdh.simpegrestapi.enums.RabbitJobType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobMessage {
    private RabbitJobType type;
}
