package com.br.cepservice.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cep_log")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CepLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cep;

    @Column(nullable = false)
    private LocalDateTime dataConsulta;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String respostaApi;
}
