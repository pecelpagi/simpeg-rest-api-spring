package com.galuhrmdh.simpegrestapi.repository;

import com.galuhrmdh.simpegrestapi.entity.Employee;
import com.galuhrmdh.simpegrestapi.entity.WarningLetter;
import com.galuhrmdh.simpegrestapi.entity.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarningLetterRepository extends JpaRepository<WarningLetter, Integer>, JpaSpecificationExecutor<WarningLetter> {

    List<WarningLetter> findWarningLettersByEmployee(Employee employee);

}
