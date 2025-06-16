package com.br.cepservice.test;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class ContainerSmokeTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @Container
    static GenericContainer<?> wiremock = new GenericContainer<>(
            DockerImageName.parse("wiremock/wiremock:2.35.0"))
            .withExposedPorts(8080);

    @Test
    void testPostgresContainerIsRunning() {
        assertTrue(postgres.isRunning());
        assertTrue(postgres.getJdbcUrl().contains("jdbc:postgresql://"));
    }

    @Test
    void testWireMockContainerIsRunning() {
        assertTrue(wiremock.isRunning());
        assertTrue(wiremock.getMappedPort(8080) > 0);
    }
}