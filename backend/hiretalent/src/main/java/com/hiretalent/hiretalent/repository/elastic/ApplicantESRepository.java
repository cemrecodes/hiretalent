package com.hiretalent.hiretalent.repository.elastic;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.hiretalent.hiretalent.entity.elastic.ApplicantModel;

public interface ApplicantESRepository extends ElasticsearchRepository<ApplicantModel, Long> {

	ApplicantModel getById(Long id);
	
	@Query("{\"query_string\": {\"query\": \"?0\",\"fields\": [\"name\", \"surname\", \"email\", \"phoneNumber\", \"link\", \"educations.schoolName\", \"educations.department\", \"educations.title\" , \"experiences.title\", \"experiences.companyPosition\", \"experiences.detail\" ,\"certificates.title\", \"certificates.issuer\", \"skills.skill\", \"languages.language\"]}}")
	List<ApplicantModel> searchApplicants(String searchText);
}
