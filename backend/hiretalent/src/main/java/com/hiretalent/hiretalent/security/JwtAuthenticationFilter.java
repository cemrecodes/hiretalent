package com.hiretalent.hiretalent.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hiretalent.hiretalent.service.ApplicantService;
import com.hiretalent.hiretalent.service.HrUserService;

import io.jsonwebtoken.Claims;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@Autowired
	private HrUserService hrUserService;

	@Autowired
	private ApplicantService applicantService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {
	    try {
	        String jwtToken = extractJwtFromRequest(request);
	        if (StringUtils.isNotBlank(jwtToken) && jwtTokenProvider.validateToken(jwtToken)) {
	            Claims claims = jwtTokenProvider.getClaimsFromJwt(jwtToken);
	            String userType = claims.get("roles", String.class);
	            Long id = Long.parseLong(claims.getSubject());

	            Object user = null;
	            if ("hr".equals(userType)) {
	                user = hrUserService.getHrUserById(id);
	            } else if ("applicant".equals(userType)) {
	                user = applicantService.getApplicantById(id);
	            }

	            if (user != null) {
	            	List<GrantedAuthority> authorities = new ArrayList<>();
	                if ("hr".equals(userType)) {
	                    authorities.add(new SimpleGrantedAuthority("ROLE_hr"));
	                } else if ("applicant".equals(userType)) {
	                    authorities.add(new SimpleGrantedAuthority("ROLE_applicant"));
	                }

	                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, authorities);
	                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                SecurityContextHolder.getContext().setAuthentication(auth);   
	            }
	        }
	    } catch (Exception e) {
            throw new RuntimeException(e);
        }
	    
	    filterChain.doFilter(request, response);
	}

	private String extractJwtFromRequest(HttpServletRequest request) {
		String bearer = request.getHeader("Authorization");
		if(StringUtils.isNotBlank(bearer) && bearer.startsWith("Bearer")) {
			return bearer.substring("Bearer".length() + 1);
		}
		return null;
	}

}

