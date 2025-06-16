package com.br.cepservice.infrastructure.gateway;

import com.br.cepservice.domain.model.Endereco;
import com.br.cepservice.infrastructure.exception.ExternalServiceException;
import com.br.cepservice.infrastructure.exception.InvalidInputException;
import com.br.cepservice.infrastructure.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

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

    private void setupBaseUrl(String baseUrl) throws Exception {
        Field baseUrlField = ExternalCepClient.class.getDeclaredField("baseUrl");
        baseUrlField.setAccessible(true);
        baseUrlField.set(externalCepClient, baseUrl);
    }

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
        setupBaseUrl(baseUrl);

        // Act
        Endereco result = externalCepClient.buscarPorCep(cep);

        // Assert
        assertEquals(expectedEndereco, result);
        verify(webClient).get();
        verify(requestHeadersUriSpec).uri("/api/cep/v1/{cep}", cep);
    }

    @Test
    void whenCepNotFound_shouldThrowResourceNotFoundException() throws Exception {
        // Arrange
        String cep = "00000000";
        String baseUrl = "/api/cep/v1";

        // Mock the WebClient fluent API chain
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(eq("/api/cep/v1/{cep}"), eq(cep)))
                .thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Endereco.class))
                .thenReturn(Mono.error(new WebClientResponseException(
                        HttpStatus.NOT_FOUND.value(),
                        "Not Found",
                        null, null, null)));

        // Set private field using reflection
        setupBaseUrl(baseUrl);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> externalCepClient.buscarPorCep(cep));
    }

    @Test
    void whenInvalidCepFormat_shouldThrowInvalidInputException() throws Exception {
        // Arrange
        String cep = "invalid";
        String baseUrl = "/api/cep/v1";

        // Mock the WebClient fluent API chain
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(eq("/api/cep/v1/{cep}"), eq(cep)))
                .thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Endereco.class))
                .thenReturn(Mono.error(new WebClientResponseException(
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Request",
                        null, null, null)));

        // Set private field using reflection
        setupBaseUrl(baseUrl);

        // Act & Assert
        assertThrows(InvalidInputException.class, () -> externalCepClient.buscarPorCep(cep));
    }

    @Test
    void whenServerError_shouldThrowExternalServiceException() throws Exception {
        // Arrange
        String cep = "01001000";
        String baseUrl = "/api/cep/v1";

        // Mock the WebClient fluent API chain
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(eq("/api/cep/v1/{cep}"), eq(cep)))
                .thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Endereco.class))
                .thenReturn(Mono.error(new WebClientResponseException(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Internal Server Error",
                        null, null, null)));

        // Set private field using reflection
        setupBaseUrl(baseUrl);

        // Act & Assert
        assertThrows(ExternalServiceException.class, () -> externalCepClient.buscarPorCep(cep));
    }

    @Test
    void whenTimeout_shouldThrowExternalServiceException() throws Exception {
        // Arrange
        String cep = "01001000";
        String baseUrl = "/api/cep/v1";

        // Mock the WebClient fluent API chain
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(eq("/api/cep/v1/{cep}"), eq(cep)))
                .thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Endereco.class))
                .thenReturn(Mono.error(new java.util.concurrent.TimeoutException("Connection timed out")));

        // Set private field using reflection
        setupBaseUrl(baseUrl);

        // Act & Assert
        assertThrows(ExternalServiceException.class, () -> externalCepClient.buscarPorCep(cep));
    }
}
