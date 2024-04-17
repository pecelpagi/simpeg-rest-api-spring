package com.galuhrmdh.simpegrestapi.entity;

import com.galuhrmdh.simpegrestapi.enums.ContractStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contracts")
public class Contract {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    @Enumerated(EnumType.STRING)
    @Column(name = "contract_status")
    private ContractStatus contractStatus;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "contract_length_month")
    private Integer contractLengthMonth;

    private String attachment;
}
