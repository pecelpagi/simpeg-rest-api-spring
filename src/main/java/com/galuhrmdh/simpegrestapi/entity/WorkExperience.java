package com.galuhrmdh.simpegrestapi.entity;

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
@Table(name  = "work_experiences")
public class WorkExperience {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "company_name")
    private String companyName;

    private String type;

    private String location;

    private String department;

    @Column(name = "employee_position")
    private String employeePosition;

    @Temporal(TemporalType.DATE)
    @Column(name = "initial_period")
    private Date initialPeriod;

    @Temporal(TemporalType.DATE)
    @Column(name = "final_period")
    private Date finalPeriod;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

}
