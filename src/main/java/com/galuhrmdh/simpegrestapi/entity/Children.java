package com.galuhrmdh.simpegrestapi.entity;

import com.galuhrmdh.simpegrestapi.enums.ChildStatus;
import com.galuhrmdh.simpegrestapi.enums.Gender;
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
@Table(name = "children")
public class Children {

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

    @Column(name = "child_sequence")
    private Integer childSequence;

    @Column(name = "last_education")
    private String lastEducation;

    @Column(name = "occupation")
    private String occupation;

    @Enumerated(EnumType.STRING)
    @Column(name = "child_status")
    private ChildStatus childStatus;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

}
