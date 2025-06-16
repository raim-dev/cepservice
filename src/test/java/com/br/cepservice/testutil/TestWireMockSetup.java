package com.br.cepservice.testutil;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class TestWireMockSetup {
    private static WireMockServer wireMockServer;

    public static void main(String[] args) {
        startServer();

        try {
            System.out.println("WireMock running at http://localhost:8081");
            System.out.println("Press Enter to stop...");
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stopServer();
        }
    }

    public static void startServer() {
        wireMockServer = new WireMockServer(
                WireMockConfiguration.options()
                        .port(8081)
                        .usingFilesUnderClasspath("wiremock")
        );
        wireMockServer.start();

        configureStubs();
    }

    private static void configureStubs() {
        // CEP endpoint stub
        wireMockServer.stubFor(get(urlPathEqualTo("/api/cep/v1/01001000"))
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

        System.out.println("Stubs configured for:");
        System.out.println("- GET /api/cep/v1/01001000");
    }

    public static void stopServer() {
        if (wireMockServer != null) {
            wireMockServer.stop();
            System.out.println("WireMock stopped");
        }
    }
}