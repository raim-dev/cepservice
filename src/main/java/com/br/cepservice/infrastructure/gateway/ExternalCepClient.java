package com.br.cepservice.infrastructure.gateway;

import com.br.cepservice.application.gateway.CepGateway;
import com.br.cepservice.domain.model.Endereco;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

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
        return webClient.get()
                .uri(baseUrl + "/{cep}", cep)
                .retrieve()
                .bodyToMono(Endereco.class)
                .block();
    }


}
