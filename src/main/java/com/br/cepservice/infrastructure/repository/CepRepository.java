package com.br.cepservice.infrastructure.repository;

import com.br.cepservice.domain.model.CepLog;

import java.util.List;
import java.util.Optional;

public interface CepRepository {

    CepLog salvar(CepLog cepLog);

    Optional<CepLog> buscarPorId(Long id);

    List<CepLog> listarTodos();
}
