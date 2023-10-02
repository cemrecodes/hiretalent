package com.hiretalent.hiretalent.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiretalent.hiretalent.entity.HrUser;
import com.hiretalent.hiretalent.entity.JobPosting;
import com.hiretalent.hiretalent.entity.JobStatus;
import com.hiretalent.hiretalent.repository.JobPostingRepository;
import com.hiretalent.hiretalent.entity.request.JobPostingCreateRequest;

@Service
public class JobPostingService {
	private final JobPostingRepository jobPostingRepository;

	private final HrUserService hrUserService;
	@Autowired
	public JobPostingService(JobPostingRepository jobPostingRepository, HrUserService hrUserService) {
		this.jobPostingRepository = jobPostingRepository;
		this.hrUserService = hrUserService;
	}

	public List<JobPosting> getAll(){
		return jobPostingRepository.findAll();
	}
		
    public JobPosting getJobPostingById(Long jobId) {
        return jobPostingRepository.findById(jobId).orElse(null);
    }

	public JobPosting create(JobPostingCreateRequest newJobPosting) {
		JobPosting toSave = new JobPosting();
		HrUser user = new HrUser();
		user = hrUserService.getHrUserById(newJobPosting.getHrUserId());
		toSave.setHrUser(user);
        toSave.setTitle(newJobPosting.getTitle());
        toSave.setDescription(newJobPosting.getDescription());
        toSave.setQualifications(newJobPosting.getQualifications());
        JobStatus statusEnum = JobStatus.valueOf(newJobPosting.getStatus());
        toSave.setStatus(statusEnum);
        toSave.setDateCreated(LocalDateTime.now());
        if(newJobPosting.getActivationDateTime() != null) {
	        LocalDateTime formattedActivationDate = LocalDateTime.parse(newJobPosting.getActivationDateTime(), DateTimeFormatter.ISO_DATE_TIME);
	        toSave.setActivationDateTime(formattedActivationDate);
	    }
        if(newJobPosting.getDeactivationDateTime() != null) {
	        LocalDateTime formattedDeactivationDate = LocalDateTime.parse(newJobPosting.getDeactivationDateTime(), DateTimeFormatter.ISO_DATE_TIME);
	        toSave.setDeactivationDateTime(formattedDeactivationDate);
        }
        JobPosting savedJob = jobPostingRepository.save(toSave);
        Long jobId = savedJob.getId();
        Long code = generateCode(jobId);
        savedJob.setCode(code);
        return jobPostingRepository.save(savedJob);
	}


    private Long generateCode(Long jobId) {
        Long code = jobId + 1000;
        return code;
    }
    
    public void closeJobPosting(Long id) {
    	JobPosting jobPosting = new JobPosting();
    	jobPosting = jobPostingRepository.findById(id).orElse(null);
    	if( jobPosting != null ) {
    		jobPosting.setStatus(JobStatus.CLOSED);        
            jobPosting.setDateClosed(LocalDateTime.now());
            jobPostingRepository.save(jobPosting);
    	}
    }

	public JobPosting save(JobPosting jobPosting) {
		return jobPostingRepository.save(jobPosting);
	}

	public List<JobPosting> findByActivationDateTimeLessThanEqualAndStatus(LocalDateTime dateTime, JobStatus status) {
		return jobPostingRepository.findByActivationDateTimeLessThanEqualAndStatus(dateTime, status);
	}

	public List<JobPosting> findByDeactivationDateTimeLessThanEqualAndStatus(LocalDateTime dateTime, JobStatus status) {
		return jobPostingRepository.findByDeactivationDateTimeLessThanEqualAndStatus(dateTime, status);
	}
	
	public List<JobPosting> getActiveAndNonClosedJobPostings(){
		return jobPostingRepository.findByDateClosedIsNullAndStatusEquals(JobStatus.ACTIVE);		
	}

	public void changeJobStatus(Long jobPostingId, JobStatus status, LocalDateTime date) {
    	JobPosting jobPosting = new JobPosting();
    	jobPosting = jobPostingRepository.findById(jobPostingId).orElse(null);
    	if( jobPosting != null && date == null ) {
    		jobPosting.setStatus(status);        
            jobPosting.setDateUpdated(LocalDateTime.now());
            jobPostingRepository.save(jobPosting);
    	}
    	else if ( jobPosting != null && status == JobStatus.ACTIVE) {
    		jobPosting.setActivationDateTime(date);
    	}
    	else if ( jobPosting != null && status == JobStatus.PASSIVE ) {
    		jobPosting.setDeactivationDateTime(date);
    	}
	}

}
