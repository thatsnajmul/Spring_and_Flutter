package com.thatsnajmul.backend.repository;

import com.thatsnajmul.backend.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Integer> {


}
