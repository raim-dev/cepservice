package com.br.cepservice.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class TestContainersConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public PostgreSQLContainer<?> postgreSQLContainer() {
        return new PostgreSQLContainer<>("postgres:15-alpine")
                .withDatabaseName("cepdbtest")
                .withUsername("testuser")
                .withPassword("testpass");
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public GenericContainer<?> wireMockContainer() {
        return new GenericContainer<>(DockerImageName.parse("wiremock/wiremock:2.35.0"))
                .withExposedPorts(8080)
                .withCommand("--verbose");
    }

    @Bean
    public WebClient webClient(GenericContainer<?> wireMockContainer) {
        String baseUrl = "http://" + wireMockContainer.getHost() +
                ":" + wireMockContainer.getMappedPort(8080);
        return WebClient.create(baseUrl);
    }
}