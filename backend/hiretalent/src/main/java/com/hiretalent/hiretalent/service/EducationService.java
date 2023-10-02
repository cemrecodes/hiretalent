package com.hiretalent.hiretalent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiretalent.hiretalent.entity.Education;
import com.hiretalent.hiretalent.repository.EducationRepository;

import java.util.List;

@Service
public class EducationService {

    private final EducationRepository educationRepository;

    @Autowired
    public EducationService(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    public List<Education> saveAllEducation(List<Education> educationList) {
        List<Education> savedEducations = educationRepository.saveAll(educationList);
        return savedEducations;
    }

}
