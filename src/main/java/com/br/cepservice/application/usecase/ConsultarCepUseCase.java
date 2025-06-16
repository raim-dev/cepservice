package com.br.cepservice.cepservice.application.usecase;

import com.br.cepservice.cepservice.domain.model.Endereco;

public interface ConsultarCepUseCase {

    Endereco executar(String cep);
}
