package com.hiretalent.hiretalent.entity.dto;

import java.time.LocalDateTime;

import com.hiretalent.hiretalent.entity.Applicant;
import com.hiretalent.hiretalent.entity.JobApplicationStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JobApplicationDTO {
    private Long id;
    private Long jobPostingId;
    private Applicant applicant;
    private LocalDateTime dateCreated;
    private LocalDateTime dateEvaluated;
    private JobApplicationStatus status;
}