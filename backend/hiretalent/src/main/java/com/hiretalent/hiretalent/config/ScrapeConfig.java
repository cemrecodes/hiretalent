package com.hiretalent.hiretalent.config;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;

@Configuration
public class ScrapeConfig {

    private static final Logger logger = LoggerFactory.getLogger(ScrapeConfig.class);

    @Value("${selenium.url}")
    private String host;

    @Value("${scrape.linkedin.email}")
    private String email;

    @Value("${scrape.linkedin.password}")
    private String password;

    @Bean
    public WebDriver webDriver() {
        logger.info("Host: {}" , host);
        ChromeOptions chromeOptions = new ChromeOptions();
        try {
            return new RemoteWebDriver(new URL(host), chromeOptions);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public CommandLineRunner loginOnStartup(WebDriver webDriver) {
        logger.info("Host: {}" , host);
        return args -> {
            webDriver.get("https://www.linkedin.com/login");
            webDriver.findElement(By.xpath("/html/body/div/main/div[2]/div[1]/form/div[1]/input")).sendKeys(email);
            webDriver.findElement(By.xpath("/html/body/div/main/div[2]/div[1]/form/div[2]/input")).sendKeys(password);
            Thread.sleep(1000);
            webDriver.findElement(By.xpath("/html/body/div/main/div[2]/div[1]/form/div[3]/button")).click();
         //   webDriver.close();
        };
    }
}
