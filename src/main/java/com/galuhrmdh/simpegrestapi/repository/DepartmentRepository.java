package com.galuhrmdh.simpegrestapi.repository;

import com.galuhrmdh.simpegrestapi.entity.Department;
import com.galuhrmdh.simpegrestapi.model.department.DepartmentRecap;
import com.galuhrmdh.simpegrestapi.model.employeeposition.EmployeePositionRecap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer>, JpaSpecificationExecutor<Department> {

    @Query(value = "select " +
            "new com.galuhrmdh.simpegrestapi.model.department.DepartmentRecap" +
            "(COUNT(e.department.id) AS total, d.name) FROM Employee e " +
            "JOIN Department d ON d.id = e.department.id " +
            "GROUP BY e.department.id")
    List<DepartmentRecap> getDepartmentRecap();


}
