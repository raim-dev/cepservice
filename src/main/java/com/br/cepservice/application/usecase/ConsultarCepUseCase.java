package com.br.cepservice.application.usecase;

import com.br.cepservice.domain.model.Endereco;

public interface ConsultarCepUseCase {

    Endereco executar(String cep);
}
