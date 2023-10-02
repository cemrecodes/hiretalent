package com.hiretalent.hiretalent.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiretalent.hiretalent.entity.response.LinkedinAuthResponse;
import com.hiretalent.hiretalent.entity.response.LinkedinContactResponse;
import com.hiretalent.hiretalent.entity.response.LinkedinProfileResponse;

@Service
public class LinkedinService {
	
	@Value("${linkedin.client_id}")
	private String CLIENT_ID;

	@Value("${linkedin.client_secret}")
	private String CLIENT_SECRET;

    private static final Logger logger = LoggerFactory.getLogger(LinkedinService.class);


    public LinkedinAuthResponse getTokenFromLinkedin(String code) {
        String REDIRECT_URL = "http://localhost:3000/";
        String url = "https://www.linkedin.com/oauth/v2/accessToken?code="
						+ code
						+ "&grant_type=authorization_code"
						+ "&client_id=" 
						+ CLIENT_ID
						+ "&client_secret="
						+ CLIENT_SECRET
						+ "&redirect_uri="
						+ REDIRECT_URL;
		 RestTemplate restTemplate = new RestTemplate();
		 logger.info("LinkedinService.getTokenFromLinkedin()");
	        try {
	            return restTemplate.postForObject(url, null, LinkedinAuthResponse.class);
	        } catch (HttpClientErrorException e) {
	            logger.error("Couldn't get token from Linkedin.");
	            logger.error(String.valueOf(e));
	        } catch (Exception e) {
	            logger.error("An exception occured.");
                logger.error(String.valueOf(e));
	        }
	        return null;
	}
	
    public LinkedinProfileResponse getLiteProfileData(String accessToken) {
        String url = "https://api.linkedin.com/v2/me";
        HttpHeaders headers = new HttpHeaders();
        String token = "Bearer " + accessToken;
        headers.set("Authorization", token );

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        try {
        	ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        	ObjectMapper objectMapper = new ObjectMapper();
        	
        	 try {
                 return objectMapper.readValue(response.getBody(), LinkedinProfileResponse.class);
             } catch (JsonProcessingException e) {
                 e.printStackTrace();
             }
        	return null;
        } catch (Exception e) {
            logger.error("Couldn't get profile data from Linkedin");
        }
        
        return null;
    }
	
    public LinkedinContactResponse getContactData(String accessToken) {
        String url = "https://api.linkedin.com/v2/clientAwareMemberHandles?q=members&projection=(elements*(primary,type,handle~))";
        HttpHeaders headers = new HttpHeaders();
        String token = "Bearer " + accessToken;
        headers.set("Authorization", token );

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        
        try {
        	ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        	ObjectMapper objectMapper = new ObjectMapper();
        	String responseString = response.getBody();
        	JsonNode jsonNode = objectMapper.readTree(responseString);
            JsonNode elementsArray = jsonNode.get("elements");

            if (elementsArray.isArray()) {
                for (JsonNode element : elementsArray) {
                    JsonNode handleDetails = element.get("handle~");

                    LinkedinContactResponse contact =  new LinkedinContactResponse();
                    if (handleDetails != null) {
                        String emailAddress = handleDetails.get("emailAddress") != null ?
                                handleDetails.get("emailAddress").asText() : null;
                        contact.setEmail(emailAddress);
                        
                        String phoneNumber = handleDetails.get("phoneNumber") != null ?
                                handleDetails.get("phoneNumber").get("number").asText() : null;
                        contact.setPhoneNumber(phoneNumber);
                        return contact;
                    }
                    }
            }
            return null;
            
        }
        catch (Exception e) {
            logger.error("Couldn't get contact data");
            logger.error(String.valueOf(e));
        }
         
        return null;
    }
}
