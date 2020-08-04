package org.nexters.az.auth.exception;

import org.nexters.az.exception.ForbiddenException;

public class TokenIsInvalidException extends ForbiddenException {
    public TokenIsInvalidException() {
        this("Token is invalid.");
    }

    public TokenIsInvalidException(String message) {
        super(message);
    }
}
