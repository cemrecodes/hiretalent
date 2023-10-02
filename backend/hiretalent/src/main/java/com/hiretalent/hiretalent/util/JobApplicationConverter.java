package com.hiretalent.hiretalent.util;

import com.hiretalent.hiretalent.entity.HrUser;
import com.hiretalent.hiretalent.entity.JobApplication;
import com.hiretalent.hiretalent.entity.elastic.ApplicantModel;
import com.hiretalent.hiretalent.entity.elastic.HrUserModel;
import com.hiretalent.hiretalent.entity.elastic.JobApplicationModel;


// Job Application entitysini elastic searche kaydetmek için uygun formata getiren metodların bulunduğu sınıf
public class JobApplicationConverter {

    public static HrUserModel convertToHrUserModel(HrUser hrUser) {
        HrUserModel hrUserModel = new HrUserModel();
        hrUserModel.setId(hrUser.getId());
        hrUserModel.setName(hrUser.getName());
        hrUserModel.setSurname(hrUser.getSurname());
        hrUserModel.setUsername(hrUser.getUsername());
        return hrUserModel;
    }
        
    public static JobApplicationModel convertToJobApplicationModel(JobApplication jobApplication) {
        JobApplicationModel jobApplicationModel = new JobApplicationModel();
        jobApplicationModel.setId(jobApplication.getId());
        jobApplicationModel.setJobPostingId(jobApplication.getJobPosting().getId());
        ApplicantModel applicantModel = ApplicantConverter.convertToApplicantModel(jobApplication.getApplicant());
        jobApplicationModel.setApplicant(applicantModel);
        jobApplicationModel.setDateCreated(jobApplication.getDateCreated());
        jobApplicationModel.setDateEvaluated(jobApplication.getDateEvaluated());
        if ( jobApplication.getEvaluatedBy() != null ) {
        	HrUserModel hrUserModel = convertToHrUserModel(jobApplication.getEvaluatedBy());
        	System.err.println("hr: " + hrUserModel);
        	jobApplicationModel.setEvaluatedBy(hrUserModel);
        }
        jobApplicationModel.setStatus(jobApplication.getStatus());
        return jobApplicationModel;
    }
    
}
