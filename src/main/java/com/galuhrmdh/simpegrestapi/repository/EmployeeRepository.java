package com.galuhrmdh.simpegrestapi.repository;

import com.galuhrmdh.simpegrestapi.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
