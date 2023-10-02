package com.hiretalent.hiretalent.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.hiretalent.hiretalent.entity.JobPosting;
import com.hiretalent.hiretalent.entity.JobStatus;


public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {

	List<JobPosting> findByActivationDateTimeLessThanEqualAndStatus(LocalDateTime dateTime, JobStatus status);

	List<JobPosting> findByDeactivationDateTimeLessThanEqualAndStatus(LocalDateTime dateTime, JobStatus status);

	List<JobPosting> findByDateClosedIsNullAndStatusEquals(JobStatus status);

}