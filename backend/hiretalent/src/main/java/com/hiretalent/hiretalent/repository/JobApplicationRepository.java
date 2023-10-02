package com.hiretalent.hiretalent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hiretalent.hiretalent.entity.Applicant;
import com.hiretalent.hiretalent.entity.JobApplication;
import com.hiretalent.hiretalent.entity.JobPosting;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

	List<JobApplication> findByJobPosting(JobPosting jobPosting);

	boolean existsByApplicantAndJobPosting(Applicant applicant, Object jobPosting);

	List<JobApplication> findByApplicant(Applicant applicant);

	List<JobApplication> findByApplicantId(Long id);

	
}
