package org.nexters.az.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ConflictException extends ResponseStatusException {
    public ConflictException() {
        this("Conflict Exception");
    }

    public ConflictException(String message){
        super(HttpStatus.FORBIDDEN, message);
    }
}