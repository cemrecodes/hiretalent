package com.hiretalent.hiretalent.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hiretalent.hiretalent.entity.Applicant;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

	Applicant findByEmail(String email);

}
