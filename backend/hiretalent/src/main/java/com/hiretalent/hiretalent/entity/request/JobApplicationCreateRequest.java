package com.hiretalent.hiretalent.entity.request;

import lombok.Data;

@Data
public class JobApplicationCreateRequest {
    private Long jobPostingId;
    private Long applicantId;
}