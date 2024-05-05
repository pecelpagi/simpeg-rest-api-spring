package com.galuhrmdh.simpegrestapi.repository;

import com.galuhrmdh.simpegrestapi.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpouseRepository extends JpaRepository<Spouse, Integer>, JpaSpecificationExecutor<Spouse> {

    List<Spouse> findSpousesByEmployee(Employee employee);

}
