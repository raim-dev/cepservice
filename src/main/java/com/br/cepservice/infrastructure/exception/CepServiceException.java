package com.br.cepservice.infrastructure.exception;

/**
 * Base exception class for all custom exceptions in the CEP Service application.
 * This class extends RuntimeException to allow for unchecked exceptions.
 */
public class CepServiceException extends RuntimeException {
    
    public CepServiceException(String message) {
        super(message);
    }
    
    public CepServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}