package com.br.cepservice.infrastructure.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
class DatabaseConnectionTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("cepdb")
            .withUsername("postgres")
            .withPassword("postgres");

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeAll
    static void beforeAll() {
        // Override properties for test context
        System.setProperty("spring.datasource.url", postgres.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgres.getUsername());
        System.setProperty("spring.datasource.password", postgres.getPassword());
    }

    @BeforeEach
    void setUp() {
        // Create test table before each test
        jdbcTemplate.execute("""
            CREATE TABLE IF NOT EXISTS test_table (
                id SERIAL PRIMARY KEY,
                name VARCHAR(100) NOT NULL
            )
            """);

        // Clear test data before each test
        jdbcTemplate.execute("TRUNCATE TABLE test_table RESTART IDENTITY");
    }

    @Test
    void testDatabaseConnection() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            assertTrue(connection.isValid(2), "Connection should be valid");
        }
    }

    @Test
    void testSchemaExists() {
        jdbcTemplate.execute("""
            CREATE TABLE IF NOT EXISTS test_table (
                id SERIAL PRIMARY KEY,
                name VARCHAR(100) NOT NULL
            )
            """);

        int result = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'public'",
                Integer.class
        );

        assertTrue(result > 0, "Schema should contain tables");
    }

    @Test
    void testCanReadWriteData() {
        // Insert test data
        jdbcTemplate.execute("INSERT INTO test_table (name) VALUES ('test_data')");

        // Verify insertion
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM test_table",
                Integer.class
        );
        assertEquals(1, count, "Should have exactly one record");

        // Verify data content
        String name = jdbcTemplate.queryForObject(
                "SELECT name FROM test_table WHERE id = 1",
                String.class
        );
        assertEquals("test_data", name, "Should retrieve inserted data");
    }

    @Test
    void testCepLogTableStructure() throws SQLException {
        try (Connection conn = dataSource.getConnection();
             ResultSet rs = conn.getMetaData().getColumns(null, null, "cep_log", null)) {

            boolean hasCepColumn = false;
            boolean hasDataConsultaColumn = false;

            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                if ("cep".equalsIgnoreCase(columnName)) hasCepColumn = true;
                if ("data_consulta".equalsIgnoreCase(columnName)) hasDataConsultaColumn = true;
            }

            assertTrue(hasCepColumn, "cep_log should have 'cep' column");
            assertTrue(hasDataConsultaColumn, "cep_log should have 'data_consulta' column");
        }
    }
}