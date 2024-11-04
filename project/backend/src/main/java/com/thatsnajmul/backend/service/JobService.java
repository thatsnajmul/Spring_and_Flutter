package com.thatsnajmul.backend.service;

import com.thatsnajmul.backend.entity.JobEntity;
import com.thatsnajmul.backend.entity.CompanyEntity;
import com.thatsnajmul.backend.repository.JobRepository;
import com.thatsnajmul.backend.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @Value("src/main/resources/static/images")
    private String uploadDir;

    public List<JobEntity> getAllJob() {
        return jobRepository.findAll();
    }

    @Transactional
    public void saveJob(JobEntity job, MultipartFile imageFile) throws IOException {
        CompanyEntity company = companyRepository.findById(job.getCompany().getId())
                .orElseThrow(() -> new RuntimeException("Company with this ID not found"));

        if (imageFile != null && !imageFile.isEmpty()) {
            String imageFilename = saveImage(imageFile, job);
            job.setImage(imageFilename);
        }

        job.setCompany(company);
        jobRepository.save(job);
    }

    public void deleteJobById(int id) {
        jobRepository.deleteById(id);
    }

    public JobEntity findJobById(int id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with this ID"));
    }

    @Transactional
    public void updateJob(int id, JobEntity updatedJob, MultipartFile imageFile) throws IOException {
        JobEntity existingJob = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with this ID"));

        existingJob.setJobTitle(updatedJob.getJobTitle());
        existingJob.setDescription(updatedJob.getDescription());
        existingJob.setRequirements(updatedJob.getRequirements());
        existingJob.setLocation(updatedJob.getLocation());
        existingJob.setMaxSalary(updatedJob.getMaxSalary());
        existingJob.setMinSalary(updatedJob.getMinSalary());
        existingJob.setCompanyName(updatedJob.getCompanyName());
        existingJob.setCompanyEmail(updatedJob.getCompanyEmail());
        existingJob.setCompanyPhone(updatedJob.getCompanyPhone());
        existingJob.setJobType(updatedJob.getJobType());
        existingJob.setPosition(updatedJob.getPosition());
        existingJob.setSkills(updatedJob.getSkills());

        CompanyEntity company = companyRepository.findById(updatedJob.getCompany().getId())
                .orElseThrow(() -> new RuntimeException("Company with this ID not found"));
        existingJob.setCompany(company);

        if (imageFile != null && !imageFile.isEmpty()) {
            String imageFilename = saveImage(imageFile, existingJob);
            existingJob.setImage(imageFilename);
        }

        jobRepository.save(existingJob);
    }

    public List<JobEntity> findJobsByCompanyName(String companyName) {
        return jobRepository.findJobsByCompanyName(companyName);
    }

    private String saveImage(MultipartFile file, JobEntity job) throws IOException {
        Path uploadPath = Paths.get(uploadDir + "/job");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String filename = job.getJobTitle() + "_" + UUID.randomUUID();
        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath);

        return filename;
    }
}
