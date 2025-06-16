package com.br.cepservice.cepservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CepLog {
    private Long id;
    private String cep;
    private LocalDateTime dataConsulta;
    private String respostaApi;
}
