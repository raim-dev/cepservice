package com.br.cepservice.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when there is an error with an external service call.
 * This exception is mapped to HTTP 503 Service Unavailable response.
 */
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class ExternalServiceException extends CepServiceException {
    
    private final String serviceName;
    
    public ExternalServiceException(String serviceName, String message) {
        super(message);
        this.serviceName = serviceName;
    }
    
    public ExternalServiceException(String serviceName, String message, Throwable cause) {
        super(message, cause);
        this.serviceName = serviceName;
    }
    
    public String getServiceName() {
        return serviceName;
    }
    
    @Override
    public String getMessage() {
        return String.format("External service error [%s]: %s", serviceName, super.getMessage());
    }
}