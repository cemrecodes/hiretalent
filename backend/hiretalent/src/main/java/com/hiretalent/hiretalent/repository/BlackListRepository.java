package com.hiretalent.hiretalent.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hiretalent.hiretalent.entity.Applicant;
import com.hiretalent.hiretalent.entity.BlackList;
import com.hiretalent.hiretalent.entity.HrUser;

public interface BlackListRepository extends JpaRepository<BlackList, Long> {

	boolean existsByApplicant(Applicant applicant);

}
