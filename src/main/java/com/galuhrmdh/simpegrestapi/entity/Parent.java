package com.galuhrmdh.simpegrestapi.entity;

import com.galuhrmdh.simpegrestapi.enums.Gender;
import com.galuhrmdh.simpegrestapi.enums.ParentStatus;
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
@Table(name = "parents")
public class Parent {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_number")
    private String idNumber;

    private String name;

    private String birthplace;

    private Date birthdate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "last_education")
    private String lastEducation;

    private String occupation;

    @Column(name = "parent_status")
    @Enumerated(EnumType.STRING)
    private ParentStatus parentStatus;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

}
