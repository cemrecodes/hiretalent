package com.hiretalent.hiretalent.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiretalent.hiretalent.entity.Applicant;
import com.hiretalent.hiretalent.entity.HrUser;
import com.hiretalent.hiretalent.entity.JobApplication;
import com.hiretalent.hiretalent.entity.JobApplicationStatus;
import com.hiretalent.hiretalent.entity.JobPosting;
import com.hiretalent.hiretalent.entity.JobStatus;
import com.hiretalent.hiretalent.entity.elastic.JobApplicationModel;
import com.hiretalent.hiretalent.repository.JobApplicationRepository;
import com.hiretalent.hiretalent.repository.elastic.JobApplicationESRepository;
import com.hiretalent.hiretalent.entity.request.JobApplicationCreateRequest;
import com.hiretalent.hiretalent.util.JobApplicationConverter;

@Service
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;

	private final HrUserService hrUserService;

    private final ApplicantService applicantService;

    private final JobPostingService jobPostingService;

    private final JobApplicationESRepository applicationESRepository;

    private final EmailService emailService;

	@Autowired
	public JobApplicationService(JobApplicationRepository jobApplicationRepository, HrUserService hrUserService, ApplicantService applicantService, JobPostingService jobPostingService, JobApplicationESRepository applicationESRepository, EmailService emailService) {
		this.jobApplicationRepository = jobApplicationRepository;
		this.hrUserService = hrUserService;
		this.applicantService = applicantService;
		this.jobPostingService = jobPostingService;
		this.applicationESRepository = applicationESRepository;
		this.emailService = emailService;
	}

	public List<JobApplication> getAll(){
		return jobApplicationRepository.findAll();
	}
		
    public JobApplication findById(Long applicationId) {
        return jobApplicationRepository.findById(applicationId).orElse(null);
    }
    
    public List<JobApplication> getApplicationsByJobId(Long id){
    	JobPosting job = jobPostingService.getJobPostingById(id);
    	if ( job != null ) {
    		List<JobApplication> jobs = jobApplicationRepository.findByJobPosting(job);
    		return jobs;
    	}
    	else
    		return null;
    }
                                  
    public String create(JobApplicationCreateRequest jobApplication) {
    	Long id = jobApplication.getApplicantId();
    	Applicant applicant = applicantService.getApplicantById(id);
    	
    	Long jobId = jobApplication.getJobPostingId();
    	JobPosting jobPosting = jobPostingService.getJobPostingById(jobId);

    	if(jobPosting.getStatus() != JobStatus.ACTIVE) {
    		return "İş ilanı aktif olmadığı için başvuramazsınız!";
    	}
        
    	boolean alreadyApplied = jobApplicationRepository.existsByApplicantAndJobPosting(applicant, jobPosting);
        
    	if (alreadyApplied) {
            return "Bu işe önceden başvurulmuş!";
        }
    	JobApplication application = new JobApplication();
    	application.setApplicant(applicantService.getApplicantById(id));
    	application.setDateCreated(LocalDateTime.now());
    	application.setJobPosting(jobPostingService.getJobPostingById(jobApplication.getJobPostingId()));
    	
    	JobApplication jobApp = jobApplicationRepository.save(application);
    	applicationESRepository.save(JobApplicationConverter.convertToJobApplicationModel(jobApp));
        return null;
    }

    public JobApplication updateStatus(Long applicationId, Long hrUserId, JobApplicationStatus newStatus) {
        JobApplication jobApplication = jobApplicationRepository.findById(applicationId).orElse(null);
        
        if (jobApplication == null) {
            return null;
        }
        
        JobApplicationModel applicationModel = applicationESRepository.findById(applicationId).orElse(null);
        HrUser hrUser = hrUserService.getHrUserById(hrUserId);
        
        if (hrUser == null) {
            return null;
        }
        
        jobApplication.setStatus(newStatus);
        jobApplication.setEvaluatedBy(hrUser);
        jobApplication.setDateEvaluated(LocalDateTime.now());
        JobApplication application = jobApplicationRepository.save(jobApplication);
        
        applicationModel.setStatus(newStatus);
        applicationModel.setEvaluatedBy(JobApplicationConverter.convertToHrUserModel(hrUser));
        applicationModel.setDateEvaluated(application.getDateCreated());
        applicationESRepository.save(applicationModel);
        
        String title = jobApplication.getJobPosting().getTitle();
        String email = jobApplication.getApplicant().getEmail();
        String nameSurname = jobApplication.getApplicant().getName()
        					+ " "
        					+ jobApplication.getApplicant().getSurname();
        
        emailService.sendEmail(title, nameSurname, email, jobApplication.getStatus());
        
        return application;
    }

	public List<JobApplication> getJobApplicationByApplicant(Long id) {
		Applicant applicant = applicantService.getApplicantById(id);
		List<JobApplication> jobApplications = jobApplicationRepository.findByApplicant(applicant);
		return jobApplications;
	}

    public List<JobApplicationModel> searchByJobPostingIdAndApplicantProperties(Long id, String searchText) {
    	return applicationESRepository.searchByJobPostingIdAndApplicantProperties(id, searchText);
    }
    
	public void rejectAllJobApplication(Long id) {
		List<JobApplication> applications = jobApplicationRepository.findByApplicantId(id);
		List<JobApplicationModel> applicationModels = applicationESRepository.findByApplicantId(id);
		
		for(int i = 0; i<applications.size(); i++) {
			JobApplication application = applications.get(i);
			application.setStatus(JobApplicationStatus.REJECTED);
			jobApplicationRepository.save(application);
			
			JobApplicationModel applicationModel = applicationModels.get(i);
			applicationModel.setStatus(JobApplicationStatus.REJECTED);
			applicationESRepository.save(applicationModel);
			
			String title = application.getJobPosting().getTitle();
	        String email = application.getApplicant().getEmail();
	        String nameSurname = application.getApplicant().getName()
	        					+ " "
	        					+ application.getApplicant().getSurname();
	        emailService.sendEmail(title , nameSurname, email, application.getStatus());
		}
	}

}
