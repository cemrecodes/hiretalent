package com.hiretalent.hiretalent.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hiretalent.hiretalent.entity.dto.JobApplicationDTO;
import com.hiretalent.hiretalent.entity.Applicant;
import com.hiretalent.hiretalent.entity.JobApplication;
import com.hiretalent.hiretalent.entity.elastic.JobApplicationModel;
import com.hiretalent.hiretalent.entity.request.JobApplicationCreateRequest;
import com.hiretalent.hiretalent.entity.request.JobApplicationEvaluateRequest;
import com.hiretalent.hiretalent.service.ApplicantService;
import com.hiretalent.hiretalent.service.BlackListService;
import com.hiretalent.hiretalent.service.JobApplicationService;

@RestController
@RequestMapping("/job-applications")
public class JobApplicationController {
    private final JobApplicationService jobApplicationService;
    private final BlackListService blackListService;
    private final ApplicantService applicantService;
    private static final Logger logger = LoggerFactory.getLogger(JobApplicationController.class);

    @Autowired
    public JobApplicationController(JobApplicationService jobApplicationService, BlackListService blackListService, ApplicantService applicantService) {
        this.jobApplicationService = jobApplicationService;
        this.blackListService = blackListService;
        this.applicantService = applicantService;
    }

    @GetMapping
    public ResponseEntity<List<JobApplicationDTO>> getJobApplications() {
        List<JobApplication> jobApplications = jobApplicationService.getAll();
        List<JobApplicationDTO> jobApplicationDTOs = jobApplications.stream()
                .map(JobApplication::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(jobApplicationDTOs, HttpStatus.OK);
    }

    @GetMapping("/job-posting/{id}")
    public ResponseEntity<List<JobApplicationDTO>> getApplicationsByJobId(@PathVariable Long id) {
        List<JobApplication> jobApplications = jobApplicationService.getApplicationsByJobId(id);
        if (jobApplications != null) {
        	List<JobApplicationDTO> jobsDTO = jobApplications.stream()
        			.map(JobApplication::toDTO)
        			.collect(Collectors.toList());
        	return ResponseEntity.ok(jobsDTO);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/applicant/{id}")
    public ResponseEntity<List<JobApplication>> getJobApplicationByApplicantId(@PathVariable Long id) {
        List<JobApplication> jobApplications = jobApplicationService.getJobApplicationByApplicant(id);
        if (jobApplications != null) {
            return new ResponseEntity<>(jobApplications, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PostMapping
    public ResponseEntity<String> createJobApplication(@RequestBody JobApplicationCreateRequest applicationCreateRequest) {
    	Long id = applicationCreateRequest.getApplicantId();
    	Applicant applicant = applicantService.getApplicantById(id);
    	if (blackListService.isBlacklisted(applicant)) {
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
        String message = jobApplicationService.create(applicationCreateRequest);
        if(message == null)
        	return new ResponseEntity<>(HttpStatus.CREATED);
        else
        	return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/evaluate")
    public ResponseEntity<JobApplicationDTO> updateJobApplicationStatus(@RequestBody JobApplicationEvaluateRequest evaluateRequest) {
    	logger.info("JobApplicationController.updateJobApplicationStatus()");
    	logger.info("Request: {}", evaluateRequest);
        JobApplication updatedJobApplication = jobApplicationService.updateStatus(evaluateRequest.getJobApplicationId(), evaluateRequest.getHrUserId(), evaluateRequest.getStatus());
        if (updatedJobApplication != null) {
            return new ResponseEntity<>(updatedJobApplication.toDTO(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
        
    @GetMapping("/search")
    public ResponseEntity<List<JobApplicationModel>> searchJobApplications(
            @RequestParam Long id,
            @RequestParam String searchText
    ) {
        List<JobApplicationModel> jobApplications = jobApplicationService.searchByJobPostingIdAndApplicantProperties(id, searchText);
        return ResponseEntity.ok(jobApplications);
    }
}
