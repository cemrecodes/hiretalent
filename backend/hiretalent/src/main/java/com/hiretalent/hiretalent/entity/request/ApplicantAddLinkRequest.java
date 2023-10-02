package com.hiretalent.hiretalent.entity.request;

import lombok.Data;

@Data
public class ApplicantAddLinkRequest {
	private Long applicantId;
	private String link;
}
