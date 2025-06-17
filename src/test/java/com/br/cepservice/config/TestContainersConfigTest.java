package com.br.cepservice.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DisabledIfEnvironmentVariable(named = "SPRING_PROFILES_ACTIVE", matches = "ci")
@ActiveProfiles("dev")
@Import(TestConfig.class)
public class TestContainersConfigTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("configtest")
            .withUsername("testuser")
            .withPassword("testpass");

    @Container
    static GenericContainer<?> wiremock = new GenericContainer<>("wiremock/wiremock:2.35.0")
            .withExposedPorts(8080);

    @Test
    void testPostgresConfiguration() {
        assertAll(
                () -> assertTrue(postgres.isRunning()),
                () -> assertNotNull(postgres.getJdbcUrl()),
                () -> assertNotNull(postgres.getUsername()),
                () -> assertNotNull(postgres.getPassword())
        );
    }

    @Test
    void testWireMockConfiguration() {
        assertAll(
                () -> assertTrue(wiremock.isRunning()),
                () -> assertTrue(wiremock.getMappedPort(8080) > 0),
                () -> assertEquals("wiremock/wiremock:2.35.0",
                        wiremock.getDockerImageName())
        );
    }
}
