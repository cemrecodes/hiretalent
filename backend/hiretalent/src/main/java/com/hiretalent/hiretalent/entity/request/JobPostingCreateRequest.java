package com.hiretalent.hiretalent.entity.request;
import lombok.Data;

@Data
public class JobPostingCreateRequest {
	Long hrUserId;
	String title;
	String description;
	String qualifications;
	String activationDateTime;
	String deactivationDateTime;
	String status;
}
