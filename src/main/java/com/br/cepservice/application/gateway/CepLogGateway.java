package com.br.cepservice.application.gateway;

import com.br.cepservice.domain.model.CepLog;

import java.time.LocalDateTime;
import java.util.List;

public interface CepLogGateway {
    List<CepLog> listarTodos();

}
