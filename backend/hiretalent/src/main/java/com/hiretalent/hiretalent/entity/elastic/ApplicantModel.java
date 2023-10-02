package com.hiretalent.hiretalent.entity.elastic;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

import java.util.List;

@Data
@Document(indexName = "applicants")
public class ApplicantModel {

    @Id
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private List<EducationModel> educations;
    private List<ExperienceModel> experiences;
    private List<CertificateModel> certificates;
    private List<SkillModel> skills;
    private List<LanguageModel> languages;
    private String link;
    
    @Field(type = FieldType.Boolean)
    private boolean blacklisted;

}