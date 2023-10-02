package com.hiretalent.hiretalent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hiretalent.hiretalent.entity.HrUser;

public interface HrUserRepository extends JpaRepository<HrUser, Long> {
	HrUser findByUsername(String username);
	
}