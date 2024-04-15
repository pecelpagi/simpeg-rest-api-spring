package com.galuhrmdh.simpegrestapi.repository;

import com.galuhrmdh.simpegrestapi.entity.Employee;
import com.galuhrmdh.simpegrestapi.entity.EmployeePosition;
import com.galuhrmdh.simpegrestapi.model.employee.EmployeeCitizenRecap;
import com.galuhrmdh.simpegrestapi.model.employeeposition.EmployeePositionRecap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {

    @Modifying
    @Query("update Notification n set n.readStatus = true where n.readStatus = false")
    void updateReadStatusAsRead();

    @Query(value = "select " +
            "new com.galuhrmdh.simpegrestapi.model.employee.EmployeeCitizenRecap" +
            "(COUNT(e.citizen) AS total, e.citizen) FROM Employee e " +
            "GROUP BY e.citizen")
    List<EmployeeCitizenRecap> getEmployeeCitizenRecap();

}
