package com.galuhrmdh.simpegrestapi.repository;

import com.galuhrmdh.simpegrestapi.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface ContractRepository extends JpaRepository<Contract, Integer>, JpaSpecificationExecutor<Contract> {

    @Query(value = "select " +
            "c.*, DATE_ADD(c.start_date, INTERVAL c.contract_length_month MONTH) as end_date, e.id_number, e.name FROM contracts c " +
            "JOIN employees e ON e.id = c.employee_id " +
            "HAVING end_date BETWEEN '2024-04-01' AND '2024-04-30'", nativeQuery = true)
    List<Map<String, Object>> getContractReminder();

}
