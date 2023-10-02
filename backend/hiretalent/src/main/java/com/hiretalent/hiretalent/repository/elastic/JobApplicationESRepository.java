package com.hiretalent.hiretalent.repository.elastic;

import java.util.List;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import com.hiretalent.hiretalent.entity.elastic.JobApplicationModel;

public interface JobApplicationESRepository extends ElasticsearchRepository<JobApplicationModel, Long>{
	
    @Query("{\"bool\": {\"must\": [{\"term\": {\"jobPostingId\": \"?0\"}}, {\"multi_match\": {\"query\": \"?1\", \"fields\": [\"applicant.name\", \"applicant.surname\", \"applicant.email\", \"applicant.phoneNumber\",\"applicant.educations.schoolName\", \"applicant.educations.department\", \"applicant.educations.title\" , \"applicant.experiences.title\", \"applicant.experiences.companyPosition\", \"applicant.experiences.detail\" ,\"applicant.certificates.title\", \"applicant.certificates.issuer\", \"applicant.skills.skill\", \"applicant.languages.language\" ,\"evaluatedBy.name\", \"evaluatedBy.email\"]}}]}}")
    List<JobApplicationModel> searchByJobPostingIdAndApplicantProperties(Long jobPostingId, String searchText);

	List<JobApplicationModel> findByApplicantId(Long id);
    
}