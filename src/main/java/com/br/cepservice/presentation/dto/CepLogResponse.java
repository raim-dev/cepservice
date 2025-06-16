package com.br.cepservice.presentation.dto;

import com.br.cepservice.domain.model.CepLog;

import java.time.LocalDateTime;

public record CepLogResponse(
        String cep,
        LocalDateTime dataConsulta,
        String respostaApi) {

    public static CepLogResponse fromDomain(CepLog log) {
        return new CepLogResponse(
                log.getCep(),
                log.getDataConsulta(),
                log.getRespostaApi()
        );
    }
}
