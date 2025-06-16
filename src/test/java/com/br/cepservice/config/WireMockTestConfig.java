package com.br.cepservice.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@TestConfiguration
public class WireMockTestConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer wireMockServer() {
        WireMockServer server = new WireMockServer(options().port(8081));

        // Configure default stubs
        server.stubFor(get(urlPathEqualTo("/api/cep/v1/01001000"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                    {
                      "cep": "01001000",
                      "state": "SP",
                      "city": "São Paulo",
                      "neighborhood": "Sé",
                      "street": "Praça da Sé"
                    }""")));

        return server;
    }
}


