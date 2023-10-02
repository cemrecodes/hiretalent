package com.hiretalent.hiretalent.entity;

import java.time.LocalDateTime;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.hiretalent.hiretalent.entity.dto.JobPostingDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "job_posting")
public class JobPosting {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
	@JoinColumn(name = "hr_user_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private HrUser hrUser;
	
	private Long code;
	private String title;
	
	@Column(length = 2000)
	private String description;
	
	@Column(length = 2000)
	private String qualifications;
		
	private LocalDateTime dateCreated;
	private LocalDateTime dateUpdated;
	private LocalDateTime dateClosed;
	
	private LocalDateTime activationDateTime;
    private LocalDateTime deactivationDateTime;

	@Enumerated(EnumType.STRING)
    private JobStatus status;
	
	public JobPostingDTO toDTO() {
        JobPostingDTO dto = new JobPostingDTO();
        dto.setId(this.id);
        dto.setCode(this.code);
        dto.setTitle(this.title);
        dto.setDescription(this.description);
        dto.setQualifications(this.qualifications);
        dto.setDateCreated(this.dateCreated);
        dto.setDateUpdated(this.dateUpdated);
        dto.setDateClosed(this.dateClosed);
        dto.setActivationDateTime(this.activationDateTime);
        dto.setDeactivationDateTime(this.deactivationDateTime);
        dto.setStatus(this.status);
        return dto;
    }

    public static JobPosting fromDTO(JobPostingDTO dto) {
        JobPosting posting = new JobPosting();
        posting.setId(dto.getId());
        posting.setCode(dto.getCode());
        posting.setTitle(dto.getTitle());
        posting.setDescription(dto.getDescription());
        posting.setQualifications(dto.getQualifications());
        posting.setDateCreated(dto.getDateCreated());
        posting.setDateUpdated(dto.getDateUpdated());
        posting.setDateClosed(dto.getDateClosed());
        posting.setActivationDateTime(dto.getActivationDateTime());
        posting.setDeactivationDateTime(dto.getDeactivationDateTime());
        posting.setStatus(dto.getStatus());
        return posting;
        
    }
	
}
