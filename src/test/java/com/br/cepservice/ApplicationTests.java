package com.br.cepservice;

import com.br.cepservice.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
@Import(TestConfig.class)
class ApplicationTests {

    @Test
    void contextLoads() {
    }

}
