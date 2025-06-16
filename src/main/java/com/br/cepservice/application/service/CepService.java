package com.br.cepservice.application.service;

import com.br.cepservice.application.gateway.CepGateway;
import com.br.cepservice.infrastructure.repository.CepRepository;
import com.br.cepservice.application.usecase.ConsultarCepUseCase;
import com.br.cepservice.domain.model.CepLog;
import com.br.cepservice.domain.model.Endereco;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CepService implements ConsultarCepUseCase {


    private final CepGateway cepGateway;
    private final CepRepository cepRepository;

    @Override
    public Endereco executar(String cep) {
        Endereco endereco = cepGateway.buscarPorCep(cep);

        CepLog log = new CepLog(
                null,
                cep,
                LocalDateTime.now(),
                endereco.toString()
        );

        cepRepository.salvar(log);

        return endereco;
    }
}