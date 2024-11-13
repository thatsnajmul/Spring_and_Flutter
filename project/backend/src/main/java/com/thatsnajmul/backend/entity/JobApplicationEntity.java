package com.thatsnajmul.backend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jobapplication")
public class JobApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    private  String applicantName;
    private String image;
    private  double expectedSalary;
    private  String applicantEmail;
    private String applicantPhone;
    private String resumeLink;
    private String applicationDate;
    private String applicationStatus;
    private String coverLetter;
    private String jobTitleApplied;
    private String skills;
    private String jobTypeApplied;
    private String locationPreference;
    private String positionLevel;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "jobId")
    private JobEntity job;




}
