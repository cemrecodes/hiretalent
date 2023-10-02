package com.hiretalent.hiretalent.entity.elastic;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class JobPostingModel {
	@Id
	private Long id;
	
}
