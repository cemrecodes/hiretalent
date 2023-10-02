package com.hiretalent.hiretalent.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "blacklist")
public class BlackList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @OneToOne
    @JoinColumn(name = "applicant_id")
    @OnDelete( action = OnDeleteAction.CASCADE)
    private Applicant applicant;
    
    @ManyToOne
    @JoinColumn(name = "hr_user_id")
    private HrUser hrUser;
    
    @Column(length = 2000)
    private String reason;
    
    
}
