package com.hiretalent.hiretalent.entity.request;

import java.time.LocalDateTime;

import com.hiretalent.hiretalent.entity.JobStatus;

import lombok.Data;

@Data
public class ChangeJobStatusRequest {

	private Long jobPostingId;
	private JobStatus status;
	private LocalDateTime date;
	
}
