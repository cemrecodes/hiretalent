package com.hiretalent.hiretalent.service;

import com.hiretalent.hiretalent.repository.elastic.ApplicantESRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ApplicantESServiceTest {
    @Mock
    private ApplicantESRepository repository;
    @InjectMocks
    private ApplicantESService service;
    @Test
    void addEducationToApplicantES() {

    }

    @Test
    void addExperienceToApplicantES() {
    }

    @Test
    void addCertificateToApplicantES() {
    }

    @Test
    void addLanguageToApplicantES() {
    }

    @Test
    void addSkillToApplicantES() {
    }
}