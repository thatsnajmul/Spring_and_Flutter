package com.thatsnajmul.backend.restcontroller;

import com.thatsnajmul.backend.entity.JobEntity;
import com.thatsnajmul.backend.service.JobService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/job")
@CrossOrigin("*")
public class JobsController {

    private final JobService jobService;

    @Autowired
    public JobsController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveJob(
            @RequestPart(value = "job") String jobJson,
            @RequestPart(value = "image") MultipartFile file
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JobEntity job = objectMapper.readValue(jobJson, JobEntity.class);

        try {
            jobService.saveJob(job, file);
            return new ResponseEntity<>("Job added successfully with image.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add job: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<JobEntity>> getAllJob() {
        List<JobEntity> jobs = jobService.getAllJob();
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/h/searchjob")
    public ResponseEntity<List<JobEntity>> findJobsByCompanyName(@RequestParam(value = "companyName") String companyName) {
        List<JobEntity> jobs = jobService.findJobsByCompanyName(companyName);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobEntity> findJobById(@PathVariable int id) {
        try {
            JobEntity job = jobService.findJobById(id);
            return ResponseEntity.ok(job);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateJob(
            @PathVariable int id,
            @RequestPart(value = "job") String jobJson,
            @RequestPart(value = "image", required = false) MultipartFile file
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JobEntity updatedJob = objectMapper.readValue(jobJson, JobEntity.class);

        try {
            jobService.updateJob(id, updatedJob, file);
            return new ResponseEntity<>("Job updated successfully.", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Failed to update job: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable int id) {
        try {
            jobService.deleteJobById(id);
            return new ResponseEntity<>("Job deleted successfully.", HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Failed to delete job: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
