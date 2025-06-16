package com.br.cepservice.infrastructure.exception;

public class CepLogPersistenceException extends RuntimeException {
    public CepLogPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
