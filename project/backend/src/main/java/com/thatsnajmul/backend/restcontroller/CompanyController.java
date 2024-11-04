package com.thatsnajmul.backend.restcontroller;

import com.thatsnajmul.backend.entity.CompanyEntity;
import com.thatsnajmul.backend.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
@CrossOrigin("*")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/")
    public ResponseEntity<List<CompanyEntity>> getAllCompanies() {
        List<CompanyEntity> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyEntity> getCompanyById(@PathVariable int id) {
        CompanyEntity company = companyService.findCompanyById(id);
        return ResponseEntity.ok(company);
    }

    @PostMapping("/")
    public ResponseEntity<CompanyEntity> saveCompany(@RequestBody CompanyEntity company) {
        CompanyEntity savedCompany = companyService.saveCompany(company);
        return ResponseEntity.ok(savedCompany);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyEntity> updateCompany(@PathVariable int id, @RequestBody CompanyEntity updatedCompany) {
        CompanyEntity company = companyService.updateCompany(id, updatedCompany);
        return ResponseEntity.ok(company);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable int id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }
}
