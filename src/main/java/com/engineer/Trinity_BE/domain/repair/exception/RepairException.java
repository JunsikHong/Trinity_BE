package com.engineer.Trinity_BE.domain.repair.exception;

import org.springframework.http.HttpStatus;

public class RepairException extends RuntimeException {

    private final HttpStatus status;

    public RepairException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }

    public RepairException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
