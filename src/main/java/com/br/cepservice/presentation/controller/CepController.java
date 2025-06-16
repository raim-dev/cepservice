package com.br.cepservice.presentation.controller;

import com.br.cepservice.application.usecase.ConsultarCepUseCase;
import com.br.cepservice.domain.model.Endereco;
import com.br.cepservice.infrastructure.exception.InvalidInputException;
import com.br.cepservice.infrastructure.exception.ResourceNotFoundException;
import com.br.cepservice.presentation.dto.CepDTO;
import com.br.cepservice.presentation.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cep")
@RequiredArgsConstructor
@Tag(name = "CEP", description = "Operations related to Brazilian postal codes (CEP)")
public class CepController {
    private final ConsultarCepUseCase consultarCepUseCase;

    @Operation(
            summary = "Get address by CEP",
            description = "Retrieves address information for a given Brazilian postal code (CEP)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Address found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CepDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid CEP format",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "CEP not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "503",
                    description = "External CEP service unavailable",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/{cep}")
    public ResponseEntity<CepDTO> consultarCep(
            @Parameter(description = "Brazilian postal code (CEP) in the format 00000000 or 00000-000", required = true)
            @PathVariable String cep) {
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
