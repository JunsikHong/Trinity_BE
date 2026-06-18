package com.engineer.Trinity_BE.domain.maintenance.exception;

import org.springframework.http.HttpStatus;

public class MaintenanceException extends RuntimeException {

    private final HttpStatus status;

    public MaintenanceException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }

    public MaintenanceException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
