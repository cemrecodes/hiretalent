package com.hiretalent.hiretalent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hiretalent.hiretalent.entity.dto.JobPostingDTO;
import com.hiretalent.hiretalent.entity.JobPosting;
import com.hiretalent.hiretalent.entity.request.ChangeJobStatusRequest;
import com.hiretalent.hiretalent.entity.request.JobPostingCreateRequest;
import com.hiretalent.hiretalent.service.JobPostingService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/job-postings")
public class JobPostingController {
    private final JobPostingService jobPostingService;
    @Autowired
    public JobPostingController(JobPostingService jobPostingService) {
        this.jobPostingService = jobPostingService;
    }

    @GetMapping
    public ResponseEntity<List<JobPostingDTO>> getAllJobPostings() {
        List<JobPosting> jobPostings = jobPostingService.getAll();
        List<JobPostingDTO> jobPostingDTOs = jobPostings.stream()
                .map(JobPosting::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(jobPostingDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobPostingDTO> getJobPostingById(@PathVariable Long id) {
        JobPosting jobPosting = jobPostingService.getJobPostingById(id);
        if (jobPosting != null) {
            return ResponseEntity.ok(jobPosting.toDTO());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<JobPostingDTO> createJobPosting(@RequestBody JobPostingCreateRequest jobPostingCreateRequest) {
        JobPosting createdJobPosting = jobPostingService.create(jobPostingCreateRequest);
        return new ResponseEntity<>(createdJobPosting.toDTO(), HttpStatus.CREATED);
    }
    

    @PutMapping("/close/{id}")
    public ResponseEntity<Void> closeJobPosting(@PathVariable Long id) {
        JobPosting jobPosting = jobPostingService.getJobPostingById(id);
        if (jobPosting != null) {
            jobPostingService.closeJobPosting(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/change-status")
    public ResponseEntity<Void> changeJobStatus(@RequestBody ChangeJobStatusRequest statusRequest) {
        JobPosting jobPosting = jobPostingService.getJobPostingById(statusRequest.getJobPostingId());
        if (jobPosting != null) {
        	jobPostingService.changeJobStatus(statusRequest.getJobPostingId(), statusRequest.getStatus(), statusRequest.getDate());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/get-active-not-closed")
    public ResponseEntity<List<JobPostingDTO>> getActiveAndNonClosedJobPostings(){
    	List<JobPosting> jobPostings = jobPostingService.getActiveAndNonClosedJobPostings();
    	List<JobPostingDTO> jobPostingDTOs = jobPostings.stream()
                .map(JobPosting::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(jobPostingDTOs, HttpStatus.OK);
    }
    
}
