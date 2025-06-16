package com.br.cepservice.cepservice.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

    @Getter
    @AllArgsConstructor
    public class Endereco {
        @JsonProperty("cep")
        private String cep;

        @JsonProperty("state")
        private String estado;

        @JsonProperty("city")
        private String cidade;

        @JsonProperty("neighborhood")
        private String bairro;

        @JsonProperty("street")
        private String rua;
    }
