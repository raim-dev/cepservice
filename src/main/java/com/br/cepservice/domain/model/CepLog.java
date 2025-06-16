package com.br.cepservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class CepLog {
    private Long id;
    private String cep;
    private LocalDateTime dataConsulta;
    private String respostaApi;
}
