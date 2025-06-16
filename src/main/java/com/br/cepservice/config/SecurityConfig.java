package com.br.cepservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Security configuration for the application.
 * In production, API key authentication is required for all endpoints.
 * In non-production environments, no authentication is required.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configures security for production environment with API key authentication.
     */
    @Bean
    @Profile("prod")
    public SecurityFilterChain securityFilterChainProd(HttpSecurity http, 
                                                      @Value("${app.security.api-keys:}") String apiKeysString) throws Exception {
        List<String> apiKeys = Arrays.asList(apiKeysString.split(","));
        
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new ApiKeyAuthFilter(apiKeys), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/health", "/actuator/info").permitAll()
                        .anyRequest().authenticated())
                .build();
    }

    /**
     * Configures security for non-production environments without authentication.
     */
    @Bean
    @Profile("!prod")
    public SecurityFilterChain securityFilterChainNonProd(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .build();
    }

    /**
     * Filter that validates API keys in the request header.
     */
    private static class ApiKeyAuthFilter extends OncePerRequestFilter {
        private static final String API_KEY_HEADER = "X-API-Key";
        private final List<String> validApiKeys;

        public ApiKeyAuthFilter(List<String> validApiKeys) {
            this.validApiKeys = validApiKeys;
        }

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
            
            // Skip filter for health and info endpoints
            String requestURI = request.getRequestURI();
            if (requestURI.equals("/actuator/health") || requestURI.equals("/actuator/info")) {
                filterChain.doFilter(request, response);
                return;
            }
            
            // Get API key from header
            String apiKey = request.getHeader(API_KEY_HEADER);
            
            // Validate API key
            if (apiKey == null || !validApiKeys.contains(apiKey)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or missing API key");
                return;
            }
            
            filterChain.doFilter(request, response);
        }
    }
}