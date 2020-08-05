package org.nexters.az.user.exception;

import org.nexters.az.exception.UnauthorizedException;

public class IdentificationOrPasswordMismatchException extends UnauthorizedException {
    public IdentificationOrPasswordMismatchException() {
        this("identification or password do not match");
    }

    public IdentificationOrPasswordMismatchException(String message) {
        super(message);
    }
}
