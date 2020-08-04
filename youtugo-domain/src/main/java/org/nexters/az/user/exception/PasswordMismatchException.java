package org.nexters.az.user.exception;

import org.nexters.az.exception.UnauthorizedException;

public class PasswordMismatchException extends UnauthorizedException {
    public PasswordMismatchException() {
        this("password do not match");
    }

    public PasswordMismatchException(String message) {
        super(message);
    }
}
