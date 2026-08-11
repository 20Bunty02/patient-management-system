package com.pm.patientservice.exception;

public class EmailAleardyExistsException extends RuntimeException {
    public EmailAleardyExistsException(String message) {
        super(message);
    }
}
