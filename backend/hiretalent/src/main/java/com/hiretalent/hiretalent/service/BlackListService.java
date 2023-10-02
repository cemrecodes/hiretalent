package com.hiretalent.hiretalent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.hiretalent.hiretalent.entity.Applicant;
import com.hiretalent.hiretalent.entity.BlackList;
import com.hiretalent.hiretalent.entity.HrUser;
import com.hiretalent.hiretalent.repository.BlackListRepository;

@Service
public class BlackListService {

    private final BlackListRepository blackListRepository;

    private final JobApplicationService applicationService;

    private final ApplicantService applicantService;

    @Autowired
    public BlackListService(BlackListRepository blackListRepository, JobApplicationService applicationService, ApplicantService applicantService) {
        this.blackListRepository = blackListRepository;
        this.applicationService = applicationService;
        this.applicantService = applicantService;
    }

    public boolean isBlacklisted(Applicant applicant) {
        return blackListRepository.existsByApplicant(applicant);
    }
        
    public BlackList addToBlackList(Applicant applicant, HrUser hrUser, String reason) {
	    	BlackList blackListEntry = new BlackList();
	        blackListEntry.setApplicant(applicant);
	        blackListEntry.setHrUser(hrUser);
	        blackListEntry.setReason(reason);
	        applicationService.rejectAllJobApplication(applicant.getId());
	        applicantService.addApplicantToBlacklist(applicant);
	        return blackListRepository.save(blackListEntry);
    }

}
