package com.hiretalent.hiretalent.service;

import java.time.Duration;
import java.util.List;
import java.util.ArrayList;

import com.hiretalent.hiretalent.entity.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiretalent.hiretalent.util.ScrapeResult;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ScrapeService {

	private final WebDriver driver;

    private final EducationService educationService;

    private final ExperienceService experienceService;

    private final CertificateService certificateService;

    private final SkillService skillService;

    private final LanguageService languageService;

    private final ApplicantESService applicantESService;
    
    @Autowired
	public ScrapeService(WebDriver driver, EducationService educationService,
						 ExperienceService experienceService,
						 CertificateService certificateService,
						 SkillService skillService,
						 LanguageService languageService,
						 ApplicantESService applicantESService) {
		this.driver = driver;
		this.educationService = educationService;
		this.experienceService = experienceService;
		this.certificateService = certificateService;
		this.skillService = skillService;
		this.languageService = languageService;
		this.applicantESService = applicantESService;
	}

	private static final Logger logger = LoggerFactory.getLogger(ScrapeService.class);
    public ScrapeResult scrapeProfile(String url, Applicant applicant) {
		logger.info("Driver in ScrapeResult: {}", driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.get(url);

            String currentUrl = driver.getCurrentUrl();

            if (currentUrl.endsWith("/404")) {
                return new ScrapeResult(false, "Kişi bulunamadı 404");
            }

            if (url.endsWith("/")) {
                url = url.substring(0, url.length() - 1);
            }

            scrapeEducationPage(url, driver, wait, applicant);
            scrapeExperiencePage(url, driver, wait, applicant);
            scrapeCertificatePage(url, driver, wait, applicant);
            scrapeLanguagePage(url, driver, wait, applicant);
            scrapeSkillPage(url, driver, wait, applicant);

			logger.info("Scrape işlemi tamamlandı.");
            return new ScrapeResult(true);
        } catch (Exception e) {
            return new ScrapeResult(false, e.getMessage());
        } finally {
            driver.quit();
        }
    }

    private void scrapeEducationPage(String url, WebDriver driver, WebDriverWait wait, Applicant applicant) {
        try {
        	// edu school
	        driver.get(url + "/details/education/");
	        List<WebElement> eduSchools = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@class, 'display-flex') and contains(@class, 'align-items-center') and contains(@class, 'mr1') and contains(@class, 'hoverable-link-text') and contains(@class, 't-bold')]/span[@aria-hidden='true']")));
	        for (WebElement school : eduSchools) {
	            String text = school.getText();
				logger.info("Edu School: {}", text);
	        }
	
		  	//edu degree
		    List<WebElement> eduDepartment = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//span[@class='t-14 t-normal']/span[@aria-hidden='true']")));
		    for (WebElement department : eduDepartment) {
		        String text = department.getText();
				logger.info("Edu Department: {}", text);
		    }
		    
		    // edu year
		    List<WebElement> eduYear = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//span[@class='t-14 t-normal t-black--light']/span[@aria-hidden='true']")));
		    for (WebElement year : eduYear) {
				logger.info(year.getText());
		    }      
		   
		    // edu detail
		    List<WebElement> eduDetails = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@class, 'inline-show-more-text') and contains(@class, 'inline-show-more-text--is-collapsed') and contains(@class, 'inline-show-more-text--is-collapsed-with-line-clamp') and contains(@class, 'full-width')]/span[@aria-hidden='true']")));
		    for (WebElement detail : eduDetails) {
		        String text = detail.getText();
				logger.info("Edu Detail: {}", text);
		    }

			List<Education> educationList = new ArrayList<>();

			for (int i = 0; i < eduSchools.size(); i++) {
				Education edu = new Education();
				edu.setApplicant(applicant);
				edu.setSchoolName(eduSchools.get(i).getText());

				if (i < eduDepartment.size()) {
					edu.setDepartment(eduDepartment.get(i).getText());
				}
				if (i < eduYear.size()) {
					edu.setTime(eduYear.get(i).getText());
				}
				if (i < eduDetails.size()) {
					edu.setDetail(eduDetails.get(i).getText());
				}
				educationList.add(edu);
			}

			List<Education> educations = educationService.saveAllEducation(educationList);
		    applicantESService.addAllEducationToApplicantES(educations, applicant.getId());
		    }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void scrapeExperiencePage(String url, WebDriver driver, WebDriverWait wait, Applicant applicant) {
        try {
        	driver.get(url+"/details/experience/");
 		    // exp title
 		    List<WebElement> expTitle = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@class, 'display-flex') and contains(@class, 'align-items-center') and contains(@class, 'mr1') and contains(@class, 't-bold')]/span[@aria-hidden='true']")));
 		    for (WebElement title : expTitle) {
 		        String text = title.getText();
				logger.info("Exp title: {}", text);
 		    }
 		
 		    // exp company and position
 		    List<WebElement> expCompanyPos = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//span[@class='t-14 t-normal']/span[@aria-hidden='true']")));
 		    for (WebElement companyPos: expCompanyPos) {
 		        String text = companyPos.getText();
				logger.info("Company and Position: {}", text);
 		    }
 		    
 		    // exp time
 		    List<WebElement> expTime = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//span[@class='t-14 t-normal t-black--light']/span[@aria-hidden='true']")));
 		    for (WebElement time : expTime) {
 		        String text = time.getText();
				logger.info("Experience Time: {}", text);
 		    }
 		
 		    // exp detail
 		    List<WebElement> expDetail = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@class, 'display-flex') and contains(@class, 'align-items-center') and contains(@class, 't-14') and contains(@class, 't-normal') and contains(@class, 't-black')]/span[@aria-hidden='true' and not(./strong)]")));
 		    for (WebElement detail : expDetail) {
 		        String text = detail.getText();
				logger.info("Detail: {}", text);
 		    }

			List<Experience> experienceList = new ArrayList<>();
 		    
 		    for(int i = 0; i< expTitle.size(); i++) {
				Experience exp = new Experience();
				exp.setApplicant(applicant);
				exp.setTitle(expTitle.get(i).getText());
				if (i < expCompanyPos.size())
					exp.setCompanyPosition(expCompanyPos.get(i).getText());
				if (i < expTime.size())
					exp.setTime(expTime.get(i).getText());
				if (i < expDetail.size())
					exp.setDetail(expDetail.get(i).getText());

				experienceList.add(exp);
				}
 		        List<Experience> experiences = experienceService.saveAllExperience(experienceList);
 		        applicantESService.addAllExperienceToApplicantES(experiences, applicant.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void scrapeCertificatePage(String url, WebDriver driver, WebDriverWait wait, Applicant applicant) {
        try {
		    // certificate
		    driver.get(url+"/details/certifications/");
		    // certificate title
		    List<WebElement> certTitle = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@class, 'display-flex') and contains(@class, 'align-items-center') and contains(@class, 'mr1') and contains(@class, 't-bold')]/span[@aria-hidden='true']")));
		    for (WebElement title: certTitle) {
		        String text = title.getText();
				logger.info("Certificate Title: {}", text);
		    }
		    
		    // certificate issuer
		    List<WebElement> certIssuer = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//span[contains(@class, 't-14') and contains(@class, 't-normal') and not(contains(@class, 't-black--light'))]/span[@aria-hidden='true']")));;
		    for (WebElement issuer : certIssuer) {
		        String text = issuer.getText();
				logger.info("Certificate Issuer: {}", text);
		    }

			List<Certificate> certificateList = new ArrayList<>();
		    
		    for(int i = 0; i<certTitle.size(); i++) {
				Certificate certificate = new Certificate();
				certificate.setApplicant(applicant);
				certificate.setTitle(certTitle.get(i).getText());
				certificate.setIssuer(certIssuer.get(i).getText());

				certificateList.add(certificate);
				}

		    List<Certificate> certificates = certificateService.saveAllCertificate(certificateList);
		    applicantESService.addAllCertificateToApplicantES(certificates, applicant.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void scrapeLanguagePage(String url, WebDriver driver, WebDriverWait wait, Applicant applicant) {
        try {
		    // languages
		    driver.get(url+"/details/languages/");	    
		    List<WebElement> languages = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@class, 'display-flex') and contains(@class, 'align-items-center') and contains(@class, 'mr1') and contains(@class, 't-bold')]/span[@aria-hidden='true']")));
		    for (WebElement language : languages) {
		        String text = language.getText();
				logger.info("Language: {}", text);
		    }

			List<Language> languageList = new ArrayList<>();

			for (WebElement webElement : languages) {
				Language lang = new Language();
				lang.setApplicant(applicant);
				lang.setLanguage(webElement.getText());

				languageList.add(lang);
			}

			List<Language> langs = languageService.saveAllLanguage(languageList);
			applicantESService.addAllLanguageToApplicantES(langs, applicant.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void scrapeSkillPage(String url, WebDriver driver, WebDriverWait wait, Applicant applicant) {
        try {
		    // skills
		    driver.get(url+"/details/skills/");	   
		    JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		    jsExecutor.executeScript("window.scrollBy(0, 500)");
		    
		       long durationInMillis = 8000;
		        long endTime = System.currentTimeMillis() + durationInMillis;

		        while (System.currentTimeMillis() < endTime) {
		            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 500)");

		            try {
		                Thread.sleep(500); // 2 saniye bekle
		            } catch (InterruptedException e) {
						Thread.currentThread().interrupt();
		                e.printStackTrace();
		            }
		        }

		    List<WebElement> skills = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[contains(@class, 'display-flex') and contains(@class, 'align-items-center') and contains(@class, 'mr1') and contains(@class, 'hoverable-link-text') and contains(@class, 't-bold')]/span[@aria-hidden='true']")));

		    for (WebElement skill : skills) {
		        String text = skill.getText();
				logger.info("Skill: {}", text);
		    }

			List<Skill> skillList = new ArrayList<>();

            for (WebElement webElement : skills) {
                Skill skill = new Skill();
                skill.setApplicant(applicant);
                if (!webElement.getText().isEmpty()) {
					skill.setSkill(webElement.getText());
				}
				skillList.add(skill);
            }

			List<Skill> s = skillService.saveAllSkill(skillList);
			applicantESService.addAllSkillToApplicantES(s, applicant.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
