package com.hiretalent.hiretalent.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hiretalent.hiretalent.entity.JobPosting;
import com.hiretalent.hiretalent.entity.JobStatus;
import com.hiretalent.hiretalent.service.JobPostingService;

@Component
public class JobPostingScheduler {

    private final JobPostingService jobPostingService;

    @Autowired
    public JobPostingScheduler(JobPostingService jobPostingService) {
        this.jobPostingService = jobPostingService;
    }

    @Scheduled(fixedRate = 3600000)
    public void checkJobPostings() {

        LocalDateTime now = LocalDateTime.now();

        List<JobPosting> jobPostingsToActivate = jobPostingService.findByActivationDateTimeLessThanEqualAndStatus(now, JobStatus.PASSIVE);
        jobPostingsToActivate.forEach(jobPosting -> {
            jobPosting.setStatus(JobStatus.ACTIVE);
            jobPostingService.save(jobPosting);
        });

        List<JobPosting> jobPostingsToDeactivate = jobPostingService.findByDeactivationDateTimeLessThanEqualAndStatus(now, JobStatus.ACTIVE);
        jobPostingsToDeactivate.forEach(jobPosting -> {
            jobPosting.setStatus(JobStatus.PASSIVE);
            jobPostingService.save(jobPosting);
        });
    }
}
