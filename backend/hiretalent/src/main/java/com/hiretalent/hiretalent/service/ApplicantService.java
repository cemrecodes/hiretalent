package com.hiretalent.hiretalent.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiretalent.hiretalent.entity.Applicant;
import com.hiretalent.hiretalent.entity.elastic.ApplicantModel;
import com.hiretalent.hiretalent.repository.ApplicantRepository;
import com.hiretalent.hiretalent.repository.elastic.ApplicantESRepository;
import com.hiretalent.hiretalent.entity.response.LinkedinAuthResponse;
import com.hiretalent.hiretalent.entity.response.LinkedinContactResponse;
import com.hiretalent.hiretalent.entity.response.LinkedinProfileResponse;
import com.hiretalent.hiretalent.util.ApplicantConverter;
import com.hiretalent.hiretalent.util.ScrapeResult;

@Service
public class ApplicantService {
	private final ApplicantRepository applicantRepository;

	private final ScrapeService scrapeService;

	private final LinkedinService linkedinService;

	private final ApplicantESRepository applicantESRepository;

	private static final Logger logger = LoggerFactory.getLogger(ApplicantService.class);

	@Autowired
	public ApplicantService(ApplicantRepository applicantRepository, ScrapeService scrapeService, LinkedinService linkedinService, ApplicantESRepository applicantESRepository) {
		this.applicantRepository = applicantRepository;
		this.scrapeService = scrapeService;
		this.linkedinService = linkedinService;
		this.applicantESRepository = applicantESRepository;
	}

	public Applicant createByLogin(LinkedinAuthResponse auth) {
		String token = auth.getAccess_token();
		LinkedinContactResponse contactRes = linkedinService.getContactData(token);
		String email = contactRes.getEmail();
		Applicant byEmail = applicantRepository.findByEmail(email);
		if( byEmail != null) {
			return byEmail;
		}
		else {
			LinkedinProfileResponse profileRes = linkedinService.getLiteProfileData(token);
			Applicant applicant = new Applicant();
			applicant.setName(profileRes.getLocalizedFirstName());
			applicant.setSurname(profileRes.getLocalizedLastName());
			applicant.setEmail(contactRes.getEmail());
			applicant.setPhoneNumber(contactRes.getPhoneNumber());
			applicant.setLink(null);
			Applicant appl = applicantRepository.save(applicant);
			applicantESRepository.save(ApplicantConverter.convertToApplicantModel(appl));
			return applicant;
		}
	}
	
	public ScrapeResult updateByScraping(Long id, String url) {
		logger.info("ApplicantService.updateByScraping()");
		Applicant applicant = applicantRepository.getReferenceById(id);
		logger.info("Applicant: {}", applicant);
        ScrapeResult result = scrapeService.scrapeProfile(url, applicant);
		logger.info("Result: {}", result);
        return result;
    }
	
	public Applicant create(Applicant applicant) {
		Applicant appl = applicantRepository.save(applicant);
		applicantESRepository.save(ApplicantConverter.convertToApplicantModel(appl));
		return appl;
	}
	
	public Applicant getApplicantById(Long id) {
		return applicantRepository.findById(id).orElse(null);
	}

	public List<Applicant> getAll(){
		return applicantRepository.findAll();
	}

	public boolean addApplicantLink(Long id, String link) {
		Applicant foundApplicant = applicantRepository.getReferenceById(id);
		ApplicantModel foundModel = applicantESRepository.getById(id);
		if( foundApplicant != null ) {
			foundApplicant.setLink(link);
			foundModel.setLink(link);
			applicantESRepository.save(foundModel);			
			return true;
		}
		return false;
	}

    public List<ApplicantModel> searchApplicants(String searchText) {
        return applicantESRepository.searchApplicants(searchText);
    }
    
    public void addApplicantToBlacklist(Applicant applicant) {
    	applicant.setBlacklisted(true);
    	applicantRepository.save(applicant);
    	ApplicantModel foundModel = applicantESRepository.getById(applicant.getId());
    	foundModel.setBlacklisted(true);
    	applicantESRepository.save(foundModel);
    	
    }

}
