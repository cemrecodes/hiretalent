package com.hiretalent.hiretalent.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hiretalent.hiretalent.entity.Applicant;
import com.hiretalent.hiretalent.entity.elastic.ApplicantModel;
import com.hiretalent.hiretalent.entity.request.ApplicantAddLinkRequest;
import com.hiretalent.hiretalent.service.ApplicantService;
import com.hiretalent.hiretalent.util.ScrapeResult;

@RestController
@RequestMapping("/applicants")
public class ApplicantController {
    private final ApplicantService applicantService;
    private static final Logger logger = LoggerFactory.getLogger(ApplicantController.class);

    @Autowired
    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @PostMapping
    public ResponseEntity<Applicant> createApplicant(@RequestBody Applicant applicant) {
        Applicant createdApplicant = applicantService.create(applicant);
        return new ResponseEntity<>(createdApplicant, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Applicant> getApplicantById(@PathVariable Long id) {
        Applicant applicant = applicantService.getApplicantById(id);
        if (applicant != null) {
            return new ResponseEntity<>(applicant, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/get-link/{id}")
    public ResponseEntity<String> getApplicantLink(@PathVariable Long id) {
        Applicant applicant = applicantService.getApplicantById(id);
        String link = applicant.getLink();
        if ( link != null) {
            return new ResponseEntity<>(link, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add-link")
    public ResponseEntity<String> addApplicantLink(@RequestBody ApplicantAddLinkRequest addLinkRequest) {
    	logger.info("ApplicantController.addApplicantLink()");
        boolean result = applicantService.addApplicantLink(addLinkRequest.getApplicantId(), addLinkRequest.getLink());
        if (result) {
        	ScrapeResult scrapeResult = applicantService.updateByScraping(addLinkRequest.getApplicantId(), addLinkRequest.getLink());
            if (scrapeResult != null )
            	return new ResponseEntity<>("Link and applicant info has been successfully added", HttpStatus.OK);
            else
            	return new ResponseEntity<>("Link has been successfully added.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Link couldn't be added.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Applicant>> getAllApplicants() {
        List<Applicant> applicants = applicantService.getAll();
        return new ResponseEntity<>(applicants, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ApplicantModel>> searchApplicants(@RequestParam("q") String searchText) {
        List<ApplicantModel> applicants = applicantService.searchApplicants(searchText);
        return ResponseEntity.ok(applicants);
    }
}

