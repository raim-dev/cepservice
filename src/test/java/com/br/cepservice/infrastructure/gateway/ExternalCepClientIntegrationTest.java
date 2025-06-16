package com.br.cepservice.cepservice.infrastructure.gateway;

import com.br.cepservice.cepservice.domain.model.Endereco;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import org.junit.jupiter.api.*;
import org.springframework.web.reactive.function.client.WebClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ExternalCepClientIntegrationTest {

    private static WireMockServer wireMockServer;
    private ExternalCepClient externalCepClient;

    @BeforeAll
    static void setup() {
        wireMockServer = new WireMockServer(
                WireMockConfiguration.options()
                        .port(8081)
                        .usingFilesUnderClasspath("wiremock")
        );
        wireMockServer.start();
    }

    @BeforeEach
    void init() {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8081")
                .build();

        externalCepClient = new ExternalCepClient(webClient, "/api/cep/v1");

        // Configure mock response with exact path matching
        wireMockServer.stubFor(get(urlPathEqualTo("/api/cep/v1/01001000"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("cep-response.json")));
    }

    @AfterAll
    static void tearDown() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    @Test
    void shouldReturnEnderecoWhenCepIsValid() {
        Endereco result = externalCepClient.buscarPorCep("01001000");

        assertEquals("01001000", result.getCep());
        assertEquals("SP", result.getEstado());
        assertEquals("São Paulo", result.getCidade());
        assertEquals("Sé", result.getBairro());
        assertEquals("Praça da Sé", result.getRua());

        // Verify the request was made
        wireMockServer.verify(1, getRequestedFor(urlEqualTo("/api/cep/v1/01001000")));
    }

    @Test
    void shouldThrowWhenCepNotFound() {
        wireMockServer.stubFor(get(urlPathMatching("/api/cep/v1/99999999"))
                .willReturn(aResponse().withStatus(404)));

        Assertions.assertThrows(RuntimeException.class, () -> {
            externalCepClient.buscarPorCep("99999999");
        });
    }
}