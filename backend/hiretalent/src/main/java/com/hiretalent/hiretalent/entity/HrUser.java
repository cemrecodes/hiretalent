package com.hiretalent.hiretalent.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EnableJpaRepositories(basePackages = "com.hiretalent.hiretalent.repository")
@Table(name = "hr_user")
public class HrUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String surname;
	private String username;
	
}
