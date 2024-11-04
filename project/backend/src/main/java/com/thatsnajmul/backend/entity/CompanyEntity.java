package com.thatsnajmul.backend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "companies")
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String companyName;
    private String companyImage;
    private String companyDetails;
    private String companyEmail;
    private String companyAddress;
    private String companyPhone;
    private int employeeSize;

    private String image;


}
