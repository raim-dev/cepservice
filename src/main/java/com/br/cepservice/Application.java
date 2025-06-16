package com.br.cepservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {

        log.info("DataSource atual: {}", dataSource.getClass().getName());

        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            log.info("Banco de dados conectado: {}", metaData.getDatabaseProductName());
            log.info("URL: {}", metaData.getURL());
        }

    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
