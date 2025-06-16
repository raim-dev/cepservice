package com.br.cepservice.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when input data is invalid.
 * This exception is mapped to HTTP 400 Bad Request response.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidInputException extends CepServiceException {
    
    public InvalidInputException(String message) {
        super(message);
    }
    
    public InvalidInputException(String field, String value) {
        super(String.format("Invalid value '%s' for field: %s", value, field));
    }
    
    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }
}