package com.hiretalent.hiretalent.entity.dto;

import java.time.LocalDateTime;

import com.hiretalent.hiretalent.entity.JobStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JobPostingDTO {
    private Long id;
    private Long code;
    private String title;
    private String description;
    private String qualifications;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private LocalDateTime dateClosed;
    private LocalDateTime activationDateTime;
    private LocalDateTime deactivationDateTime;
    private JobStatus status;

}
