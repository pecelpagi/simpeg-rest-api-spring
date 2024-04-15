package com.galuhrmdh.simpegrestapi.repository;

import com.galuhrmdh.simpegrestapi.entity.Education;
import com.galuhrmdh.simpegrestapi.model.education.EducationRecap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationRepository extends JpaRepository<Education, Integer>, JpaSpecificationExecutor<Education> {

    @Query(value = "select " +
            "new com.galuhrmdh.simpegrestapi.model.education.EducationRecap" +
            "(COUNT(e.educationLevel) AS total, e.educationLevel) FROM Education e " +
            "GROUP BY e.educationLevel")
    public List<EducationRecap> getEducationRecap();

}
