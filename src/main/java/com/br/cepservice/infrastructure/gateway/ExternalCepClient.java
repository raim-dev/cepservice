package com.br.cepservice.infrastructure.gateway;

import com.br.cepservice.application.gateway.CepGateway;
import com.br.cepservice.domain.model.Endereco;
import com.br.cepservice.infrastructure.exception.ExternalServiceException;
import com.br.cepservice.infrastructure.exception.InvalidInputException;
import com.br.cepservice.infrastructure.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;

@Component
//@RequiredArgsConstructor
public class ExternalCepClient implements CepGateway {

    private final WebClient webClient;
    private final String baseUrl;

    public ExternalCepClient(WebClient webClient, @Value("${app.external-cep-api.base-url}") String baseUrl) {
        this.webClient = webClient;
        this.baseUrl = baseUrl;
    }

    public WebClient getWebClient() {
        return this.webClient;
    }

    @Override
    public Endereco buscarPorCep(String cep) {
        try {
            return webClient.get()
                    .uri(baseUrl + "/{cep}", cep)
                    .retrieve()
                    .bodyToMono(Endereco.class)
                    .timeout(Duration.ofSeconds(5))
                    .block();
        } catch (WebClientResponseException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ResourceNotFoundException("CEP não encontrado: " + cep);
            } else if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new InvalidInputException("Formato de CEP inválido: " + cep);
            } else {
                throw new ExternalServiceException("CEP-API", "Erro ao consultar o serviço externo", ex);
            }
        } catch (Exception ex) {
            throw new ExternalServiceException("CEP-API", "Erro ao consultar o serviço externo", ex);
        }
    }


}
