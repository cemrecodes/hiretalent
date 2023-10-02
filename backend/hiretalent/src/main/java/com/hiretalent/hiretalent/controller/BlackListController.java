package com.hiretalent.hiretalent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hiretalent.hiretalent.entity.Applicant;
import com.hiretalent.hiretalent.entity.BlackList;
import com.hiretalent.hiretalent.entity.HrUser;
import com.hiretalent.hiretalent.entity.request.BlackListRequest;
import com.hiretalent.hiretalent.service.ApplicantService;
import com.hiretalent.hiretalent.service.BlackListService;
import com.hiretalent.hiretalent.service.HrUserService;

@RestController
@RequestMapping("/blacklist")
public class BlackListController {

	private final BlackListService blackListService;

	private final ApplicantService applicantService;

	private final HrUserService hrUserService;
	@Autowired
	public BlackListController(BlackListService blackListService, ApplicantService applicantService, HrUserService hrUserService) {
		this.blackListService = blackListService;
		this.applicantService = applicantService;
		this.hrUserService = hrUserService;
	}

    @PostMapping
    public ResponseEntity<BlackList> addToBlackList(@RequestBody BlackListRequest blackListRequest) {
    	Applicant applicant = applicantService.getApplicantById(blackListRequest.getApplicantId());
    	HrUser hrUser = hrUserService.getHrUserById(blackListRequest.getHrUserId());
    	if(blackListService.isBlacklisted(applicant)) {
    		return new ResponseEntity<>(HttpStatus.OK);
    	}
    	BlackList blackListed = blackListService.addToBlackList(applicant, hrUser, blackListRequest.getReason());
        return new ResponseEntity<>(blackListed, HttpStatus.CREATED);
    }
	
}
