package com.br.cepservice.cepservice.application.service;

import com.br.cepservice.cepservice.application.gateway.CepGateway;
import com.br.cepservice.cepservice.application.repository.CepRepository;
import com.br.cepservice.cepservice.application.usecase.ConsultarCepUseCase;
import com.br.cepservice.cepservice.domain.model.CepLog;
import com.br.cepservice.cepservice.domain.model.Endereco;
import lombok.AllArgsConstructor;
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