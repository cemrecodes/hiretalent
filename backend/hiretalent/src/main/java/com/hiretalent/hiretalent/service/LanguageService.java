package com.hiretalent.hiretalent.service;

import com.hiretalent.hiretalent.entity.Education;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiretalent.hiretalent.entity.Language;
import com.hiretalent.hiretalent.repository.LanguageRepository;

import java.util.List;

@Service
public class LanguageService {

    private final LanguageRepository languageRepository;

    @Autowired
    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public List<Language> saveAllLanguage(List<Language> languageList) {
        List<Language> savedLanguages = languageRepository.saveAll(languageList);
        return savedLanguages;
    }

}
