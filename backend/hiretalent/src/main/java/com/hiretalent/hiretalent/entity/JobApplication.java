package com.hiretalent.hiretalent.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.hiretalent.hiretalent.entity.dto.JobApplicationDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "job_application")
public class JobApplication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @ManyToOne
    @JoinColumn(name = "jobPosting_id")
    @OnDelete( action = OnDeleteAction.CASCADE)
    private JobPosting jobPosting;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    @OnDelete( action = OnDeleteAction.CASCADE)
    private Applicant applicant;
    
    private LocalDateTime dateCreated;
    private LocalDateTime dateEvaluated;
    
    @ManyToOne
    @JoinColumn(name = "hr_user_id")
    @OnDelete( action = OnDeleteAction.CASCADE)
    private HrUser evaluatedBy;
    
    @Enumerated(EnumType.STRING)
    private JobApplicationStatus status;
    
    public JobApplicationDTO toDTO() {
        JobApplicationDTO dto = new JobApplicationDTO();
        dto.setId(this.id);
        dto.setJobPostingId(this.jobPosting.getId());
        dto.setApplicant(this.getApplicant());
        dto.setDateCreated(this.dateCreated);
        dto.setDateEvaluated(this.dateEvaluated);
        dto.setStatus(this.status);
        return dto;
    }

    public static JobApplication fromDTO(JobApplicationDTO dto) {
        JobApplication application = new JobApplication();
        application.setId(dto.getId());
        application.setDateCreated(dto.getDateCreated());
        application.setDateEvaluated(dto.getDateEvaluated());
        application.setStatus(dto.getStatus());
        return application;
    }
}
