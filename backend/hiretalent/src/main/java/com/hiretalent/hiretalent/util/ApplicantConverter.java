package com.hiretalent.hiretalent.util;

import java.util.List;
import java.util.ArrayList;

import com.hiretalent.hiretalent.entity.Applicant;
import com.hiretalent.hiretalent.entity.Certificate;
import com.hiretalent.hiretalent.entity.Education;
import com.hiretalent.hiretalent.entity.Experience;
import com.hiretalent.hiretalent.entity.Language;
import com.hiretalent.hiretalent.entity.Skill;
import com.hiretalent.hiretalent.entity.elastic.ApplicantModel;
import com.hiretalent.hiretalent.entity.elastic.CertificateModel;
import com.hiretalent.hiretalent.entity.elastic.EducationModel;
import com.hiretalent.hiretalent.entity.elastic.ExperienceModel;
import com.hiretalent.hiretalent.entity.elastic.LanguageModel;
import com.hiretalent.hiretalent.entity.elastic.SkillModel;

public class ApplicantConverter {
	
	public static ApplicantModel convertToApplicantModel(Applicant applicant) {
		ApplicantModel applicantModel = new ApplicantModel();
	    applicantModel.setId(applicant.getId());
	    applicantModel.setName(applicant.getName());
	    applicantModel.setSurname(applicant.getSurname());
	    applicantModel.setEmail(applicant.getEmail());
	    applicantModel.setPhoneNumber(applicant.getPhoneNumber());
	    applicantModel.setLink(applicant.getLink());

	    List<EducationModel> educations = new ArrayList<>();
	    for (Education education : applicant.getEducations()) {
	        educations.add(convertToEducationModel(education));
	    }
	    applicantModel.setEducations(educations);

	    List<ExperienceModel> experiences = new ArrayList<>();
	    for (Experience experience : applicant.getExperiences()) {
	        experiences.add(convertToExperienceModel(experience));
	    }
	    applicantModel.setExperiences(experiences);

	    List<CertificateModel> certificates = new ArrayList<>();
	    for (Certificate certificate : applicant.getCertificates()) {
	        certificates.add(convertToCertificateModel(certificate));
	    }
	    applicantModel.setCertificates(certificates);

	    List<SkillModel> skills = new ArrayList<>();
	    for (Skill skill : applicant.getSkills()) {
	         skills.add(convertToSkillModel(skill));
	    }
	    applicantModel.setSkills(skills);

	    List<LanguageModel> languages = new ArrayList<>();
	    for (Language language : applicant.getLanguages()) {
	        languages.add(convertToLanguageModel(language));
	    }
	    applicantModel.setLanguages(languages);
	    applicantModel.setBlacklisted(false);
	    System.out.println("converter: " + applicantModel);
	    return applicantModel;
	    }

	public static EducationModel convertToEducationModel(Education education) {
        EducationModel educationModel = new EducationModel();
        educationModel.setId(education.getId());
	    educationModel.setSchoolName(education.getSchoolName());
	    educationModel.setDepartment(education.getDepartment());
	    educationModel.setTime(education.getTime());
        educationModel.setDetail(education.getDetail());

        if (education.getApplicant() != null) {
        	educationModel.setApplicantId(education.getApplicant().getId());
	    }

	    return educationModel;
	}

	public static ExperienceModel convertToExperienceModel(Experience experience) {
	    ExperienceModel experienceModel = new ExperienceModel();
	    experienceModel.setId(experience.getId());
	    experienceModel.setTitle(experience.getTitle());
	    experienceModel.setCompanyPosition(experience.getCompanyPosition());
	    experienceModel.setTime(experience.getTime());
	    experienceModel.setDetail(experience.getDetail());
	    
	    if (experience.getApplicant() != null) {
	        experienceModel.setApplicantId(experience.getApplicant().getId());
	    }

	    return experienceModel;
	}

	public static CertificateModel convertToCertificateModel(Certificate certificate) {
	    CertificateModel certificateModel = new CertificateModel();
	    certificateModel.setId(certificate.getId());
	    certificateModel.setTitle(certificate.getTitle());
	    certificateModel.setIssuer(certificate.getIssuer());
	    
        if (certificate.getApplicant() != null) {
            certificateModel.setApplicantId(certificate.getApplicant().getId());
        }

	        return certificateModel;
	}

	public static SkillModel convertToSkillModel(Skill skill) {
		SkillModel skillModel = new SkillModel();
	    skillModel.setId(skill.getId());
	    skillModel.setSkill(skill.getSkill());
	    
	    if (skill.getApplicant() != null) {
	    	skillModel.setApplicantId(skill.getApplicant().getId());
	    }

	    return skillModel;
	}

	public static LanguageModel convertToLanguageModel(Language language) {
		LanguageModel languageModel = new LanguageModel();
	    languageModel.setId(language.getId());
	    languageModel.setLanguage(language.getLanguage());
	    
	    if (language.getApplicant() != null) {
	    	languageModel.setApplicantId(language.getApplicant().getId());
	    }

	    return languageModel;
	}

}
