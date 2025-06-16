package com.br.cepservice.infrastructure.repository;

import com.br.cepservice.domain.model.CepLog;
import com.br.cepservice.infrastructure.entity.CepLogEntity;
import com.br.cepservice.infrastructure.exception.CepLogPersistenceException;
import com.br.cepservice.infrastructure.mapper.CepLogMapper;
import com.br.cepservice.infrastructure.repository.jpa.CepLogJpaRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class CepLogRepositoryImpl implements CepRepository {
    private final CepLogJpaRepository jpaRepository;
    private final CepLogMapper mapper;

    public CepLogRepositoryImpl(CepLogJpaRepository jpaRepository, CepLogMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public CepLog salvar(CepLog cepLog) {
        try {
            log.debug("Salvando log para CEP: {}", cepLog.getCep());
            // Validação básica do objeto de domínio
            Objects.requireNonNull(cepLog, "O objeto CepLog não pode ser nulo");
            Objects.requireNonNull(cepLog.getCep(), "O CEP não pode ser nulo");

            // Conversão para entidade
            CepLogEntity entity = mapper.toEntity(cepLog);
            if (entity.getDataConsulta() == null) {
                entity.setDataConsulta(LocalDateTime.now());
            }

            CepLogEntity savedEntity = jpaRepository.saveAndFlush(entity);

            log.info("Log salvo com ID: {}", savedEntity.getId());

            return mapper.toDomain(savedEntity);
        } catch (Exception e) {
            System.err.println("Erro ao salvar log de CEP: " + e.getMessage());
            throw new CepLogPersistenceException("Falha ao persistir log de CEP", e);
        }
    }


    @Override
    public Optional<CepLog> buscarPorId(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<CepLog> listarTodos() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
