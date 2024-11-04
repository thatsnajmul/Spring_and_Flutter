package com.thatsnajmul.backend.service;

import com.thatsnajmul.backend.entity.JobEntity;
import com.thatsnajmul.backend.entity.JobApplicationEntity;
import com.thatsnajmul.backend.repository.JobRepository;
import com.thatsnajmul.backend.repository.JobApplicationRepository;
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
public class JobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private JobRepository jobRepository;

    @Value("src/main/resources/static/images")
    private String uploadDir;

    public List<JobApplicationEntity> getAllJobApplications() {
        return jobApplicationRepository.findAll();
    }

    @Transactional
    public void saveJobApplication(JobApplicationEntity jobApplication, MultipartFile imageFile) throws IOException {
        JobEntity job = jobRepository.findById(jobApplication.getJob().getId())
                .orElseThrow(() -> new RuntimeException("Job with this ID not found"));

        if (imageFile != null && !imageFile.isEmpty()) {
            String imageFilename = saveImage(imageFile, jobApplication);
            jobApplication.setImage(imageFilename);
        }

        jobApplication.setJob(job);
        jobApplicationRepository.save(jobApplication);
    }

    public void deleteJobApplication(int id) {
        jobApplicationRepository.deleteById(id);
    }

    public JobApplicationEntity findById(int id) {
        return jobApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job Application not found by this ID"));
    }

    public List<JobApplicationEntity> findJobApplicationsByJobTitle(String jobTitle) {
        return jobApplicationRepository.findJobApplicationsByJobTitle(jobTitle);
    }

    public List<JobApplicationEntity> findJobApplicationsByJobId(int jobId) {
        return jobApplicationRepository.findJobApplicationsByJobId(jobId);
    }

    @Transactional
    public void updateJobApplication(int id, JobApplicationEntity updatedJobApplication, MultipartFile imageFile) throws IOException {
        JobApplicationEntity existingApplication = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job Application not found"));

        existingApplication.setApplicantName(updatedJobApplication.getApplicantName());
        existingApplication.setApplicationStatus(updatedJobApplication.getApplicationStatus());
        existingApplication.setJob(updatedJobApplication.getJob());

        if (imageFile != null && !imageFile.isEmpty()) {
            String imageFilename = saveImage(imageFile, existingApplication);
            existingApplication.setImage(imageFilename);
        }

        jobApplicationRepository.save(existingApplication);
    }

    private String saveImage(MultipartFile file, JobApplicationEntity jobApplication) throws IOException {
        Path uploadPath = Paths.get(uploadDir, "job-application");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String filename = jobApplication.getApplicationStatus() + "_" + UUID.randomUUID().toString() + ".jpg";
        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath);

        return filename;
    }
}
