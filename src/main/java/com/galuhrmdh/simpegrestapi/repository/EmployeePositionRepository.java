package com.galuhrmdh.simpegrestapi.repository;

import com.galuhrmdh.simpegrestapi.entity.EmployeePosition;
import com.galuhrmdh.simpegrestapi.model.employeeposition.EmployeePositionRecap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeePositionRepository extends JpaRepository<EmployeePosition, Integer>, JpaSpecificationExecutor<EmployeePosition> {

    @Query(value = "select " +
            "new com.galuhrmdh.simpegrestapi.model.employeeposition.EmployeePositionRecap" +
            "(COUNT(e.employeePosition.id) AS total, ep.name) FROM Employee e " +
            "JOIN EmployeePosition ep ON ep.id = e.employeePosition.id " +
            "GROUP BY e.employeePosition.id")
    List<EmployeePositionRecap> getEmployeePositionRecap();

}
