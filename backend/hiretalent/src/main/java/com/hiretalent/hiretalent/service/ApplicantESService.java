package com.hiretalent.hiretalent.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.hiretalent.hiretalent.repository.elastic.ApplicantESRepository;
import com.hiretalent.hiretalent.util.ApplicantConverter;

@Service
public class ApplicantESService {

	private final ApplicantESRepository applicantESRepository;

	@Autowired
	public ApplicantESService(ApplicantESRepository applicantESRepository) {
		this.applicantESRepository = applicantESRepository;
	}

	public void addAllEducationToApplicantES(List<Education> educationList, Long id) {
		Optional<ApplicantModel> optionalApplicant = applicantESRepository.findById(id);

		if (optionalApplicant.isPresent()) {
			ApplicantModel applicantModel = optionalApplicant.get();

			List<EducationModel> educationModels = educationList.stream()
					.map(ApplicantConverter::convertToEducationModel)
					.collect(Collectors.toList());

			List<EducationModel> existingEducations = applicantModel.getEducations();
			existingEducations.addAll(educationModels);
			applicantModel.setEducations(existingEducations);

			applicantESRepository.save(applicantModel);
		}
	}

	public void addAllExperienceToApplicantES(List<Experience> experienceList, Long id) {
		Optional<ApplicantModel> optionalApplicant = applicantESRepository.findById(id);

		if (optionalApplicant.isPresent()) {
			ApplicantModel applicantModel = optionalApplicant.get();

			List<ExperienceModel> edxperienceModels = experienceList.stream()
					.map(ApplicantConverter::convertToExperienceModel)
					.collect(Collectors.toList());

			List<ExperienceModel> existingExperiences = applicantModel.getExperiences();
			existingExperiences.addAll(existingExperiences);
			applicantModel.setExperiences(existingExperiences);

			applicantESRepository.save(applicantModel);
		}
	}
	
	public void addAllCertificateToApplicantES(List<Certificate> certificateList, Long id) {
		Optional<ApplicantModel> optionalApplicant = applicantESRepository.findById(id);

		if (optionalApplicant.isPresent()) {
			ApplicantModel applicantModel = optionalApplicant.get();

			List<CertificateModel> certificateModels = certificateList.stream()
					.map(ApplicantConverter::convertToCertificateModel)
					.collect(Collectors.toList());

			List<CertificateModel> existingCertificates = applicantModel.getCertificates();
			existingCertificates.addAll(existingCertificates);
			applicantModel.setCertificates(existingCertificates);

			applicantESRepository.save(applicantModel);
		}
	}

	public void addAllLanguageToApplicantES(List<Language> languageList, Long id) {
		Optional<ApplicantModel> optionalApplicant = applicantESRepository.findById(id);

		if (optionalApplicant.isPresent()) {
			ApplicantModel applicantModel = optionalApplicant.get();

			List<LanguageModel> languageModels = languageList.stream()
					.map(ApplicantConverter::convertToLanguageModel)
					.collect(Collectors.toList());

			List<LanguageModel> existingLanguages = applicantModel.getLanguages();
			existingLanguages.addAll(existingLanguages);
			applicantModel.setLanguages(existingLanguages);

			applicantESRepository.save(applicantModel);
		}
	}
	
	public void addAllSkillToApplicantES(List<Skill> skillList, Long id) {
		Optional<ApplicantModel> optionalApplicant = applicantESRepository.findById(id);

		if (optionalApplicant.isPresent()) {
			ApplicantModel applicantModel = optionalApplicant.get();

			List<SkillModel> skillModels = skillList.stream()
					.map(ApplicantConverter::convertToSkillModel)
					.collect(Collectors.toList());

			List<SkillModel> existingSkills = applicantModel.getSkills();
			existingSkills.addAll(existingSkills);
			applicantModel.setSkills(existingSkills);

			applicantESRepository.save(applicantModel);
		}
	}
	
}
