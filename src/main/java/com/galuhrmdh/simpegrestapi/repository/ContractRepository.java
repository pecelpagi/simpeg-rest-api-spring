package com.galuhrmdh.simpegrestapi.repository;

import com.galuhrmdh.simpegrestapi.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContractRepository extends JpaRepository<Contract, Integer>, JpaSpecificationExecutor<Contract> {
}
