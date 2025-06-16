package com.br.cepservice.presentation.dto.controller;

import com.br.cepservice.application.usecase.ConsultarCepUseCase;
import com.br.cepservice.domain.model.Endereco;
import com.br.cepservice.presentation.dto.CepDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cep")
@RequiredArgsConstructor
public class CepController {
    private final ConsultarCepUseCase consultarCepUseCase;

    @GetMapping("/{cep}")
    public ResponseEntity<CepDTO> consultarCep(@PathVariable String cep) {
        Endereco endereco = consultarCepUseCase.executar(cep);
        if (endereco == null) {
            return ResponseEntity.notFound().build();
        }

        CepDTO response = new CepDTO(
                endereco.getCep(),
                endereco.getEstado(),
                endereco.getCidade(),
                endereco.getBairro(),
                endereco.getRua()
        );

        return ResponseEntity.ok(response);
    }

}