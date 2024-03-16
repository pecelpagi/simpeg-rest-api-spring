package com.galuhrmdh.simpegrestapi.entity;

import com.galuhrmdh.simpegrestapi.enums.*;
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
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_number")
    private String idNumber;

    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    @Temporal(TemporalType.DATE)
    @Column(name = "entry_date")
    private Date entryDate;

    private String address;

    private String city;

    @Column(name = "origin_city")
    private String originCity;

    private String birthplace;

    @Temporal(TemporalType.DATE)
    private Date birthdate;

    @ManyToOne
    @JoinColumn(name = "employee_position_id", referencedColumnName = "id")
    private EmployeePosition employeePosition;

    @Enumerated(EnumType.STRING)
    private Religion religion;

    @Enumerated(EnumType.STRING)
    private Citizen citizen;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status")
    private MaritalStatus maritalStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "income_tax_status")
    private IncomeTaxStatus incomeTaxStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_type")
    private BloodType bloodType;

    @Column(name = "bpjs_health")
    private String bpjsHealth;

    @Column(name = "bpjs_employment")
    private String bpjsEmployment;

    @Column(name = "bpjs_retirement")
    private String bpjsRetirement;

    @Column(name = "profile_picture")
    private String profilePicture;
}
