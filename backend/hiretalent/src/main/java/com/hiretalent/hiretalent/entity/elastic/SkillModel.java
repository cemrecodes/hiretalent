package com.hiretalent.hiretalent.entity.elastic;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;

@Data
@Document(indexName = "skills")
public class SkillModel {

    @Id
    private Long id;
    private String skill;
    private Long applicantId; 

}
