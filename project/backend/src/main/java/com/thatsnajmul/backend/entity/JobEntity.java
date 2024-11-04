package com.thatsnajmul.backend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "job")
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    private String jobTitle;
    private String description;
    private String requirements;
    private String location;
    private Double maxSalary;
    private Double minSalary;
    private String companyName;
    private String companyEmail;
    private String companyPhone;
    private String jobType;
    private String position;
    private String skills;
    private String image;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "companyId")
    private CompanyEntity company;



}
