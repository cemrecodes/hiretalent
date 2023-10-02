package com.hiretalent.hiretalent.service;

import com.hiretalent.hiretalent.entity.Applicant;
import com.hiretalent.hiretalent.entity.HrUser;
import com.hiretalent.hiretalent.entity.request.ApplicantLoginRequest;
import com.hiretalent.hiretalent.entity.request.AuthRequest;
import com.hiretalent.hiretalent.entity.response.AuthResponse;
import com.hiretalent.hiretalent.entity.response.LinkedinAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final HrUserService hrUserService;
    private final LinkedinService linkedinService;
    private final ApplicantService applicantService;

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, HrUserService hrUserService, LinkedinService linkedinService, ApplicantService applicantService) {
        this.authenticationManager = authenticationManager;
        this.hrUserService = hrUserService;
        this.linkedinService = linkedinService;
        this.applicantService = applicantService;
    }

    public AuthResponse hrLogin(AuthRequest request){
        String username = request.getUsername();
        String password = request.getPassword();
        AuthResponse authResponse = new AuthResponse();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            HrUser user = hrUserService.getUserByUsername(username);

            if( user == null ){
                user = hrUserService.createByLogin(username);
            }

            authResponse.setMessage("Successfully logged in.");
            authResponse.setUserId(user.getId());
            authResponse.setRole("Hr");
            return authResponse;
        } catch (Exception e) {
            logger.info("Error: {}", e.getMessage());
            authResponse.setMessage(e.getMessage());
            authResponse.setUserId(null);
            authResponse.setRole(null);
            return authResponse;
        }
    }

    public AuthResponse applicantLogin(ApplicantLoginRequest request){
        LinkedinAuthResponse auth = linkedinService.getTokenFromLinkedin(request.getCode());
        logger.info("Auth: {}", auth);
        AuthResponse authResponse = new AuthResponse();

        if( auth != null) {
            Applicant applicant = applicantService.createByLogin(auth);
            authResponse.setMessage("Successfully logged in.");
            authResponse.setUserId(applicant.getId());
            authResponse.setRole("Applicant");
            return authResponse;
        }
        authResponse.setMessage("There was a problem logging in.");
        return authResponse;
    }
}
