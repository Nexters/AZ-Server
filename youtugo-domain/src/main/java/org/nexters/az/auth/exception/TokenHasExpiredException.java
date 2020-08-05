package org.nexters.az.auth.exception;

import org.nexters.az.exception.ForbiddenException;

public class TokenHasExpiredException extends ForbiddenException {
    public TokenHasExpiredException() {
        this("Token has expired");
    }

    public TokenHasExpiredException(String message) {
        super(message);
    }
}
