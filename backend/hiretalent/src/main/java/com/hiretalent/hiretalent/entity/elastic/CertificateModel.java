package com.hiretalent.hiretalent.entity.elastic;

import org.springframework.data.elasticsearch.annotations.Document;

import jakarta.persistence.Id;
import lombok.Data;

@Data
@Document(indexName = "certificates")
public class CertificateModel {
    @Id
    private Long id;
    private String title;
    private String issuer;
    private Long applicantId;
}
