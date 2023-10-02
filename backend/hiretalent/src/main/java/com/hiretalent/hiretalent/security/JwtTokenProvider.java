package com.hiretalent.hiretalent.security;

import java.util.Date;

import com.hiretalent.hiretalent.config.ScrapeConfig;
import com.hiretalent.hiretalent.service.ScrapeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hiretalent.hiretalent.entity.Applicant;
import com.hiretalent.hiretalent.entity.HrUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	@Value("${hiretalent.secret}")
	private String APP_SECRET;

	@Value("${hiretalent.expires-in}")
	private long EXPIRES_IN;

	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

	public String generateJwtToken(Object entity, String entityType) {
		if (entityType.equals("HrUser")) {
			HrUser hrUser = (HrUser) entity;
			Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);
			return Jwts.builder()
					.setSubject(Long.toString(hrUser.getId()))
					.claim("roles", "hr")
					.setIssuedAt(new Date())
					.setExpiration(expireDate)
					.signWith(Keys.hmacShaKeyFor(APP_SECRET.getBytes()))
					.compact();
		} else if (entityType.equals("Applicant")) {
			Applicant applicant = (Applicant) entity;
			Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);

			return Jwts.builder()
					.setSubject(Long.toString(applicant.getId()))
					.claim("roles", "applicant")
					.setIssuedAt(new Date())
					.setExpiration(expireDate)
					.signWith(Keys.hmacShaKeyFor(APP_SECRET.getBytes()))
					.compact();
		}
		return null;
	}

	boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
					.setSigningKey(Keys.hmacShaKeyFor(APP_SECRET.getBytes()))
					.build()
					.parseClaimsJws(token);

			return !isTokenExpired(token);
		} catch (JwtException e) {
			return false;
		}
	}

	private boolean isTokenExpired(String token) {
		try {
			Claims claims = Jwts.parserBuilder()
					.setSigningKey(Keys.hmacShaKeyFor(APP_SECRET.getBytes()))
					.build()
					.parseClaimsJws(token)
					.getBody();

			Date expiration = claims.getExpiration();
			Date currentDate = new Date();
			long tokenExpirationMillis = expiration.getTime();
			long currentMillis = currentDate.getTime();
			long tokenValidityPeriod = (long) 7 * 24 * 60 * 60 * 1000; // 1 hafta

			return (currentMillis - tokenExpirationMillis) > tokenValidityPeriod;
		} catch (JwtException | IllegalArgumentException e) {
			logger.error("Token verify error: {}", e.getMessage());
			return false;
		}
	}

	public Claims getClaimsFromJwt(String token) {
		try {
			return Jwts.parserBuilder()
					.setSigningKey(Keys.hmacShaKeyFor(APP_SECRET.getBytes()))
					.build()
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
