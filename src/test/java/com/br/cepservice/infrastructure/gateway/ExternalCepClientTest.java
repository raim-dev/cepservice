package com.br.cepservice.infrastructure.gateway;

import com.br.cepservice.domain.model.Endereco;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExternalCepClientTest {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private ExternalCepClient externalCepClient;

    @Test
    void whenValidCep_shouldReturnEndereco() throws Exception {
        // Arrange
        String cep = "01001000";
        String baseUrl = "/api/cep/v1";
        Endereco expectedEndereco = new Endereco(
                "01001000", "SP", "São Paulo", "Sé", "Praça da Sé"
        );

        // Mock the WebClient fluent API chain
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(eq("/api/cep/v1/{cep}"), eq(cep)))
                .thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Endereco.class))
                .thenReturn(Mono.just(expectedEndereco));

        // Set private field using reflection
        Field baseUrlField = ExternalCepClient.class.getDeclaredField("baseUrl");
        baseUrlField.setAccessible(true);
        baseUrlField.set(externalCepClient, baseUrl);

        // Act
        Endereco result = externalCepClient.buscarPorCep(cep);

        // Assert
        assertEquals(expectedEndereco, result);
        verify(webClient).get();
        verify(requestHeadersUriSpec).uri("/api/cep/v1/{cep}", cep);
    }
}