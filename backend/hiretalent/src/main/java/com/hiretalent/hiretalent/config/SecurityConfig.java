package com.hiretalent.hiretalent.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.hiretalent.hiretalent.security.JwtAuthenticationEntrypoint;
import com.hiretalent.hiretalent.security.JwtAuthenticationFilter;
import com.hiretalent.hiretalent.security.CookieCsrfFilter;

@Configuration
public class SecurityConfig {

    @Value("${ldap.url}")
    private String ldapUrl;

    @Value("${ldap.dc}")
    private String dc;

    @Value("${ldap.dc2}")
    private String dc2;

	private final JwtAuthenticationEntrypoint handler;

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    public SecurityConfig(JwtAuthenticationEntrypoint handler){
        this.handler = handler;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth
					.requestMatchers("/blacklist/**").hasRole("hr")
					.requestMatchers(HttpMethod.POST,"/applicants").hasRole("applicant")
					.requestMatchers(HttpMethod.GET, "/applicants/{id}").authenticated()
					.requestMatchers("/applicants/get-link/{id}").authenticated()
					.requestMatchers("/applicants/update").hasRole("applicant")
					.requestMatchers("/applicants/add-link").authenticated()
					.requestMatchers(HttpMethod.GET, "/applicants").hasRole("hr")
					.requestMatchers("/applicants/search").hasRole("hr")
					.requestMatchers(HttpMethod.GET, "/blacklist").hasRole("hr")
					.requestMatchers("/job-applications/{id}").hasRole("hr")
					.requestMatchers("/job-applications/job-posting/{id}").hasRole("hr")
					.requestMatchers("/job-applications/applicant/{id}").authenticated()
					.requestMatchers(HttpMethod.POST, "/job-applications").hasRole("applicant")
					.requestMatchers("/job-applications/evaluate").hasRole("hr")
					.requestMatchers("/job-applications/search").hasRole("hr")
					.requestMatchers("/job-postings/hr-user/{id}").hasRole("hr")
					.requestMatchers(HttpMethod.POST, "/job-postings").hasRole("hr")
					.requestMatchers("/job-postings/close/{id}").hasRole("hr")
					.requestMatchers("/job-postings/change-status").hasRole("hr")
					.requestMatchers(HttpMethod.DELETE, "/job-postings/{id}").hasRole("hr")
					.anyRequest().permitAll())
					.cors(Customizer.withDefaults())
                .csrf((csrf) -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(handler));
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new CookieCsrfFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
	}

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
    	return new JwtAuthenticationFilter();
    }
    
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*"); 
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        config.setAllowCredentials(true); 
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
    
    @Bean
    public LdapContextSource getLdapContextSource() {
    	  LdapContextSource contextSource = new LdapContextSource();
          logger.info("Url: {}", ldapUrl);
          contextSource.setUrl(ldapUrl);
          String base = "dc=" + dc + ",dc=" + dc2;
          logger.info("Base: {}", base);
          contextSource.setBase(base);
          contextSource.setPassword("userPassword");
          contextSource.afterPropertiesSet();
          return contextSource;   
    }
    
    @Bean
    AuthenticationManager ldapAuthenticationManager(
            LdapContextSource contextSource) {
    	LdapBindAuthenticationManagerFactory factory = 
            new LdapBindAuthenticationManagerFactory(contextSource);
        factory.setUserDnPatterns("uid={0}");
        return factory.createAuthenticationManager();
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(getLdapContextSource());
    }

}
