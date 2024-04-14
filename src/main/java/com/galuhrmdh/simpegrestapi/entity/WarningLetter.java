package com.galuhrmdh.simpegrestapi.entity;

import com.galuhrmdh.simpegrestapi.enums.ParentStatus;
import com.galuhrmdh.simpegrestapi.enums.Regarding;
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
@Table(name  = "warning_letters")
public class WarningLetter {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_facing_hrd")
    private Date dateFacingHrd;

    @Temporal(TemporalType.DATE)
    @Column(name = "violation_date")
    private Date violationDate;

    @Enumerated(EnumType.STRING)
    private Regarding regarding;

    @Column(name = "violation_1")
    private String violation1;

    @Column(name = "violation_2")
    private String violation2;

    @Temporal(TemporalType.DATE)
    @Column(name = "suspension_period")
    private Date suspensionPeriod;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

}
