package com.br.cepservice.application.service;

import com.br.cepservice.application.gateway.CepGateway;
import com.br.cepservice.infrastructure.repository.CepRepository;
import com.br.cepservice.domain.model.CepLog;
import com.br.cepservice.domain.model.Endereco;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CepServiceTest {
    @Mock
    private CepGateway cepGateway;

    @Mock
    private CepRepository cepRepository;

    @InjectMocks
    private CepService cepService;

    @Test
    void quandoConsultarCep_deveRetornarEnderecoESalvarLog() {
        // Arrange
        String cep = "01001000";
        Endereco enderecoEsperado = new Endereco(
                "01001000", "SP", "São Paulo", "Sé", "Praça da Sé"
        );

        when(cepGateway.buscarPorCep(cep)).thenReturn(enderecoEsperado);
        when(cepRepository.salvar(any(CepLog.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Endereco enderecoRetornado = cepService.executar(cep);

        // Assert
        assertEquals(enderecoEsperado, enderecoRetornado);
        verify(cepGateway, times(1)).buscarPorCep(cep);
        verify(cepRepository, times(1)).salvar(any(CepLog.class));
    }
}