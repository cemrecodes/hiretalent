package com.hiretalent.hiretalent.service;

import com.hiretalent.hiretalent.entity.Education;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiretalent.hiretalent.entity.Experience;
import com.hiretalent.hiretalent.repository.ExperienceRepository;

import java.util.List;

@Service
public class ExperienceService {

    private final ExperienceRepository experienceRepository;

    @Autowired
    public ExperienceService(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    public List<Experience> saveAllExperience(List<Experience> experienceList) {
        List<Experience> savedExperiences = experienceRepository.saveAll(experienceList);
        return savedExperiences;
    }

}
