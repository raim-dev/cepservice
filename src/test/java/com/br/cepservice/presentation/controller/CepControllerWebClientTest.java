package com.br.cepservice.cepservice.presentation.controller;

import com.br.cepservice.cepservice.application.usecase.ConsultarCepUseCase;
import com.br.cepservice.cepservice.config.WebClientConfig;
import com.br.cepservice.cepservice.domain.model.Endereco;
import com.br.cepservice.cepservice.presentation.dto.controller.CepController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = CepController.class)
@Import(WebClientConfig.class)
class CepControllerWebClientTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ConsultarCepUseCase consultarCepUseCase;

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public ConsultarCepUseCase consultarCepUseCase() {
            return mock(ConsultarCepUseCase.class);
        }
    }

    @BeforeEach
    void setup() {
        // Reset mocks before each test
        Mockito.reset(consultarCepUseCase);
    }

    @Test
    void whenValidCep_shouldReturnEndereco() {
        // 1. Create a proper non-null Endereco response
        Endereco mockResponse = new Endereco(
                "01001000",
                "SP",
                "São Paulo",
                "Sé",
                "Praça da Sé"
        );

        // 2. Properly mock the use case execution
        when(consultarCepUseCase.executar("01001000"))
                .thenReturn(mockResponse);

        // 3. Execute and verify
        webTestClient.get()
                .uri("/api/cep/01001000")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.cep").isEqualTo("01001000")
                .jsonPath("$.state").isEqualTo("SP")
                .jsonPath("$.city").isEqualTo("São Paulo")
                .jsonPath("$.neighborhood").isEqualTo("Sé")
                .jsonPath("$.street").isEqualTo("Praça da Sé");
    }

    @Test
    void whenCepNotFound_shouldReturn404() {
        // Mock null response for not found case
        when(consultarCepUseCase.executar("00000000"))
                .thenReturn(null);

        webTestClient.get()
                .uri("/api/cep/00000000")
                .exchange()
                .expectStatus().isNotFound();
    }
}
