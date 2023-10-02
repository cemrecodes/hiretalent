package com.hiretalent.hiretalent.entity.elastic;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;

@Data
@Document(indexName = "languages")
public class LanguageModel {

    @Id
    private Long id;
    private String language;
    private Long applicantId;

}
