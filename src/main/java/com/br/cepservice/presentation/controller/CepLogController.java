package com.br.cepservice.presentation.controller;

import com.br.cepservice.application.usecase.ConsultarLogsUseCase;
import com.br.cepservice.domain.model.CepLog;
import com.br.cepservice.presentation.dto.CepLogResponse;
import com.br.cepservice.presentation.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
@Tag(name = "Logs", description = "Operations related to CEP lookup logs")
public class CepLogController {

    private final ConsultarLogsUseCase consultarLogsUseCase;

    @Operation(
            summary = "List all CEP lookup logs",
            description = "Retrieves a list of all CEP lookup logs stored in the system"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Logs retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CepLogResponse.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping
    public ResponseEntity<List<CepLogResponse>> listarTodosLogs() {

        List<CepLog> logs = consultarLogsUseCase.listarTodos();
        List<CepLogResponse> response = logs.stream()
                .map(CepLogResponse::fromDomain)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

}
