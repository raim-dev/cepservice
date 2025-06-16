package com.br.cepservice.cepservice.application.gateway;

import com.br.cepservice.cepservice.domain.model.Endereco;

public interface CepGateway {

    Endereco buscarPorCep(String cep);
}
