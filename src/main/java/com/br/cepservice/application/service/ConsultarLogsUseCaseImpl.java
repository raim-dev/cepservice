package com.br.cepservice.application.service;

import com.br.cepservice.application.gateway.CepLogGateway;
import com.br.cepservice.application.usecase.ConsultarLogsUseCase;
import com.br.cepservice.domain.model.CepLog;
import com.br.cepservice.infrastructure.mapper.CepLogMapper;
import com.br.cepservice.infrastructure.repository.jpa.CepLogJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsultarLogsUseCaseImpl implements ConsultarLogsUseCase {

    private final CepLogJpaRepository jpaRepository;
    private final CepLogMapper mapper;

    @Override
    public List<CepLog> listarTodos() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
