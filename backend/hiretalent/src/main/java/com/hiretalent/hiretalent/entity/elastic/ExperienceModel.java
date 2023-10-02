package com.hiretalent.hiretalent.entity.elastic;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;

@Data
@Document(indexName = "experiences")
public class ExperienceModel {

    @Id
    private Long id;
    private String title;
    private String companyPosition;
    private String time;
    private String detail;
    private Long applicantId;

}
