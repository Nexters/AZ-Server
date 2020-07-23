package org.nexters.az.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ForbiddenException extends ResponseStatusException {

    public ForbiddenException(){
        this("Forbidden Exception");
    }
    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
