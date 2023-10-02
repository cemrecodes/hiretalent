package com.hiretalent.hiretalent.entity.request;

import lombok.Data;

@Data
public class BlackListRequest {
	private Long hrUserId;
	private Long applicantId;
	private String reason;
}
