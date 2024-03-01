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
@Table(name = "spouses")
public class Spouse {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_number")
    private String idNumber;

    private String name;

    private String birthplace;

    private Date birthdate;

    @Column(name = "date_of_marriage")
    private Date dateOfMarriage;

    @Column(name = "spouse_sequence")
    private Integer spouseSequence;

    @Column(name = "last_education")
    private String lastEducation;

    private String occupation;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

}
