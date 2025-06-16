package com.br.cepservice.presentation.controller;


import com.br.cepservice.application.usecase.ConsultarLogsUseCase;
import com.br.cepservice.domain.model.CepLog;
import com.br.cepservice.presentation.dto.CepLogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class CepLogController {

    private final ConsultarLogsUseCase consultarLogsUseCase;

    @GetMapping
    public ResponseEntity<List<CepLogResponse>> listarTodosLogs() {

        List<CepLog> logs = consultarLogsUseCase.listarTodos();
        List<CepLogResponse> response = logs.stream()
                .map(CepLogResponse::fromDomain)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

}
