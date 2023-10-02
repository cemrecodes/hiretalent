package com.hiretalent.hiretalent.entity.request;

import com.hiretalent.hiretalent.entity.JobApplicationStatus;

import lombok.Data;

@Data
public class JobApplicationEvaluateRequest {
	private Long jobApplicationId;
	private Long hrUserId;
	private JobApplicationStatus status;
}
