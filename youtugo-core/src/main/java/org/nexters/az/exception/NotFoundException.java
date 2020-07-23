package org.nexters.az.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {

    public NotFoundException(){
        this("NotFound Exception");
    }
    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
