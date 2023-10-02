package com.hiretalent.hiretalent.controller;

import com.hiretalent.hiretalent.entity.Applicant;
import com.hiretalent.hiretalent.entity.HrUser;
import com.hiretalent.hiretalent.entity.request.ApplicantLoginRequest;
import com.hiretalent.hiretalent.service.ApplicantService;
import com.hiretalent.hiretalent.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hiretalent.hiretalent.entity.request.AuthRequest;
import com.hiretalent.hiretalent.entity.response.AuthResponse;
import com.hiretalent.hiretalent.security.JwtTokenProvider;
import com.hiretalent.hiretalent.service.HrUserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Value("${ldap.url}")
	private String ldapUrl;

	@Value("${ldap.dc}")
	private String dc;

	@Value("${ldap.dc2}")
	private String dc2;

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	private final JwtTokenProvider jwtTokenProvider;
	private final HrUserService hrUserService;
	private final ApplicantService applicantService;

	private final AuthService authService;


	@Autowired
	public AuthController(JwtTokenProvider jwtTokenProvider, HrUserService hrUserService, ApplicantService applicantService, AuthService authService) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.hrUserService = hrUserService;
		this.applicantService = applicantService;
		this.authService = authService;
	}

	@PostMapping("/hr-login")
	public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest loginRequest) {
		AuthResponse response = authService.hrLogin(loginRequest);

		if( response.getUserId() != null ){
			HrUser user = hrUserService.getHrUserById(response.getUserId());
			String jwtToken = jwtTokenProvider.generateJwtToken(user, "HrUser");
			HttpHeaders responseHeaders = new HttpHeaders();
			
			String bearerToken = "Bearer " + jwtToken;
			responseHeaders.set("Access-Control-Expose-Headers", "X-Auth-Token");
			responseHeaders.set("X-Auth-Token", bearerToken );
			return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
	    }
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	  }

	@PostMapping("/applicant-login")
	public ResponseEntity<AuthResponse> applicantLogin(@RequestBody ApplicantLoginRequest applicantLoginRequest){
		AuthResponse authResponse = authService.applicantLogin(applicantLoginRequest);

		if( authResponse.getUserId() != null) {
			Applicant applicant = applicantService.getApplicantById(authResponse.getUserId());
			HttpHeaders responseHeaders = new HttpHeaders();
			String jwtToken = jwtTokenProvider.generateJwtToken(applicant, "Applicant");
			logger.info("Token for applicant: {}", jwtToken);
			responseHeaders.set("X-Auth-Token", "Bearer " + jwtToken );
			responseHeaders.set("Access-Control-Expose-Headers", "X-Auth-Token");
			return new ResponseEntity<>(authResponse, responseHeaders, HttpStatus.OK);
		}
		return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request) {
		request.getSession(false).invalidate();
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
