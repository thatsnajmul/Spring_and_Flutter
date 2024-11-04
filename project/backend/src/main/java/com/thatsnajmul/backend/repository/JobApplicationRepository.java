package com.thatsnajmul.backend.repository;

import com.thatsnajmul.backend.entity.JobApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplicationEntity, Integer> {


    @Query("Select r from JobApplicationEntity r Where r.job.jobTitle= :jobTitle")
    public List<JobApplicationEntity> findJobApplicationsByJobTitle(@Param("jobTitle") String jobTitle);

    @Query("Select r from JobApplicationEntity r Where r.job.id= :jobid")
    public List<JobApplicationEntity> findJobApplicationsByJobId(@Param("jobid") int jobid);


}
