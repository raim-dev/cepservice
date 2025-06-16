package com.br.cepservice.application.repository;

import com.br.cepservice.domain.model.CepLog;

public interface CepRepository {

    CepLog salvar(CepLog cepLog);
}
