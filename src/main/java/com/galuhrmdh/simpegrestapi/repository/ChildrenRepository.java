package com.galuhrmdh.simpegrestapi.repository;

import com.galuhrmdh.simpegrestapi.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ChildrenRepository extends JpaRepository<Children, Integer>, JpaSpecificationExecutor<Children> {

    List<Children> findChildrenByEmployee(Employee employee);

}
