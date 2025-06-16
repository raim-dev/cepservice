package com.br.cepservice.application.service;

import com.br.cepservice.application.gateway.CepLogGateway;
import com.br.cepservice.application.usecase.ConsultarLogsUseCase;
import com.br.cepservice.domain.model.CepLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultarLogsUseCaseImpl implements ConsultarLogsUseCase {

    private final CepLogGateway cepLogGateway;

    @Override
    public List<CepLog> listarTodos() {
        return cepLogGateway.listarTodos();
    }
}
