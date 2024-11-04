package com.thatsnajmul.backend.restcontroller;

import com.thatsnajmul.backend.entity.JobApplicationEntity;
import com.thatsnajmul.backend.service.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/job-application")
@CrossOrigin("*")
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobApplicationService;

    @PostMapping("/save")
    public ResponseEntity<String> saveJobApplication(
            @RequestPart(value = "jobApplication") JobApplicationEntity jobApplication,
            @RequestParam(value = "image", required = true) MultipartFile file
    ) throws IOException {
        jobApplicationService.saveJobApplication(jobApplication, file);
        return new ResponseEntity<>("Job Application added successfully with image.", HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<JobApplicationEntity>> getAllJobApplications() {
        List<JobApplicationEntity> jobApplications = jobApplicationService.getAllJobApplications();
        return ResponseEntity.ok(jobApplications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobApplicationEntity> findJobApplicationById(@PathVariable int id) {
        try {
            JobApplicationEntity jobApplication = jobApplicationService.findById(id);
            return ResponseEntity.ok(jobApplication);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/search-by-job-title")
    public ResponseEntity<List<JobApplicationEntity>> findJobApplicationByJobTitle(@RequestParam("jobTitle") String jobTitle) {
        List<JobApplicationEntity> jobApplications = jobApplicationService.findJobApplicationsByJobTitle(jobTitle);
        return ResponseEntity.ok(jobApplications);
    }

    @GetMapping("/search-by-job-id")
    public ResponseEntity<List<JobApplicationEntity>> findJobApplicationByJobId(@RequestParam("jobId") int jobId) {
        List<JobApplicationEntity> jobApplications = jobApplicationService.findJobApplicationsByJobId(jobId);
        return ResponseEntity.ok(jobApplications);
    }

    // PUT mapping to update a job application
    @PutMapping("/{id}")
    public ResponseEntity<String> updateJobApplication(
            @PathVariable int id,
            @RequestPart(value = "jobApplication") JobApplicationEntity updatedJobApplication,
            @RequestParam(value = "image", required = false) MultipartFile file
    ) throws IOException {
        try {
            jobApplicationService.updateJobApplication(id, updatedJobApplication, file);
            return ResponseEntity.ok("Job Application updated successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job Application not found.");
        }
    }

    // DELETE mapping to delete a job application
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobApplication(@PathVariable int id) {
        try {
            jobApplicationService.deleteJobApplication(id);
            return ResponseEntity.ok("Job Application deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job Application not found.");
        }
    }
}
