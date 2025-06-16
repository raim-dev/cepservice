package com.br.cepservice.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * Test configuration for database connections.
 * This ensures that the correct driver is used for PostgreSQL connections in CI/CD environments.
 */
@TestConfiguration
public class TestDatabaseConfig {

    private final Environment env;

    public TestDatabaseConfig(Environment env) {
        this.env = env;
    }

    /**
     * Creates a DataSource bean for test environments.
     * This ensures that the correct driver is used based on the database URL:
     * - PostgreSQL driver for PostgreSQL URLs
     * - H2 driver for H2 URLs
     * - Default driver detection for other URLs
     */
    @Bean
    @Primary
    public DataSource dataSource() {
        String url = env.getProperty("spring.datasource.url");
        String username = env.getProperty("spring.datasource.username");
        String password = env.getProperty("spring.datasource.password");

        // If URL contains postgresql, use the PostgreSQL driver
        if (url != null && url.contains("postgresql")) {
            return DataSourceBuilder.create()
                    .url(url)
                    .username(username)
                    .password(password)
                    .driverClassName("org.postgresql.Driver")
                    .build();
        }

        // If URL contains h2, use the H2 driver
        if (url != null && url.contains("h2")) {
            return DataSourceBuilder.create()
                    .url(url)
                    .username(username)
                    .password(password)
                    .driverClassName("org.h2.Driver")
                    .build();
        }

        // Otherwise, use the default configuration with driver detection
        return DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .build();
    }
}
