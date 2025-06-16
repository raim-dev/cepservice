package com.br.cepservice.application.usecase;

import com.br.cepservice.domain.model.CepLog;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultarLogsUseCase {

    List<CepLog> listarTodos();

}
