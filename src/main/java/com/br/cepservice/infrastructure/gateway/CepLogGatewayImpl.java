package com.br.cepservice.infrastructure.gateway;

import com.br.cepservice.application.gateway.CepLogGateway;
import com.br.cepservice.domain.model.CepLog;
import com.br.cepservice.infrastructure.mapper.CepLogMapper;
import com.br.cepservice.infrastructure.repository.jpa.CepLogJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CepLogGatewayImpl implements CepLogGateway {

    private final CepLogJpaRepository jpaRepository;
    private final CepLogMapper mapper;

    @Override
    public List<CepLog> listarTodos() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

}
