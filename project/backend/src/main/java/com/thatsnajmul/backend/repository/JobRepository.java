package com.thatsnajmul.backend.repository;


import com.thatsnajmul.backend.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, Integer> {


    @Query("SELECT h FROM JobEntity h WHERE h.company.companyName = :companyName")
    List<JobEntity> findJobsByCompanyName(@Param("companyName") String companyName);


}
