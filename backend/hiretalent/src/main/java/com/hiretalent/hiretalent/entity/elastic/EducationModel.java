package com.hiretalent.hiretalent.entity.elastic;

import org.springframework.data.elasticsearch.annotations.Document;

import jakarta.persistence.Id;
import lombok.Data;

@Data
@Document(indexName = "educations")
public class EducationModel {

    @Id
    private Long id;
    private String schoolName;
    private String department;
    private String time;
    private String detail;
    private Long applicantId;

}