package com.hiretalent.hiretalent.service;

import com.hiretalent.hiretalent.entity.Education;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiretalent.hiretalent.entity.Certificate;
import com.hiretalent.hiretalent.repository.CertificateRepository;

import java.util.List;

@Service
public class CertificateService {

    private final CertificateRepository certificateRepository;

    @Autowired
    public CertificateService(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    public List<Certificate> saveAllCertificate(List<Certificate> certificateList) {
        List<Certificate> savedCertificates = certificateRepository.saveAll(certificateList);
        return savedCertificates;
    }

}
