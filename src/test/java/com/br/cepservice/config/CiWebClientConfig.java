package com.br.cepservice.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClient configuration for CI environment.
 * This configuration is used when running tests in the CI environment,
 * where TestContainers is not available.
 */
@TestConfiguration
@Profile("ci")
public class CiWebClientConfig {

    /**
     * Creates a WebClient bean for the CI environment.
     * In CI, we use a fixed URL for WireMock.
     */
    @Bean
    public WebClient webClient() {
        // In CI, WireMock is available at localhost:8081
        return WebClient.create("http://localhost:8081");
    }
}