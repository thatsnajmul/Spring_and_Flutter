package com.thatsnajmul.backend.service;

import com.thatsnajmul.backend.entity.CompanyEntity;
import com.thatsnajmul.backend.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<CompanyEntity> getAllCompanies() {
        return companyRepository.findAll();
    }

    public CompanyEntity saveCompany(CompanyEntity company) {
        return companyRepository.save(company);
    }

    public void deleteCompany(int id) {
        companyRepository.deleteById(id);
    }

    public CompanyEntity findCompanyById(int id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found by this ID"));
    }

    public CompanyEntity updateCompany(int id, CompanyEntity updatedCompany) {
        CompanyEntity existingCompany = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found by this ID"));

        existingCompany.setCompanyName(updatedCompany.getCompanyName());
        existingCompany.setCompanyImage(updatedCompany.getCompanyImage());
        existingCompany.setCompanyDetails(updatedCompany.getCompanyDetails());
        existingCompany.setCompanyEmail(updatedCompany.getCompanyEmail());
        existingCompany.setCompanyAddress(updatedCompany.getCompanyAddress());
        existingCompany.setCompanyPhone(updatedCompany.getCompanyPhone());
        existingCompany.setEmployeeSize(updatedCompany.getEmployeeSize());
        existingCompany.setImage(updatedCompany.getImage());  // If needed

        return companyRepository.save(existingCompany);
    }
}
