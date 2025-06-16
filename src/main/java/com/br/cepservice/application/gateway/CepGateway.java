package com.br.cepservice.application.gateway;

import com.br.cepservice.domain.model.Endereco;

public interface CepGateway {

    Endereco buscarPorCep(String cep);
}
