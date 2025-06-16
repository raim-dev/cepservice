package com.br.cepservice.cepservice.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "cep_log")
@Getter
@Setter
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
