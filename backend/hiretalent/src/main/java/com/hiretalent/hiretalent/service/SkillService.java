package com.hiretalent.hiretalent.service;

import com.hiretalent.hiretalent.entity.Education;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiretalent.hiretalent.entity.Skill;
import com.hiretalent.hiretalent.repository.SkillRepository;

import java.util.List;

@Service
public class SkillService {

    private final SkillRepository skillRepository;
    @Autowired
    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<Skill> saveAllSkill(List<Skill> skillList) {
        List<Skill> savedSkills = skillRepository.saveAll(skillList);
        return savedSkills;
    }

}
