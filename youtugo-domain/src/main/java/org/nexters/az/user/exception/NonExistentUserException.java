package org.nexters.az.user.exception;

import org.nexters.az.exception.NotFoundException;

public class NonExistentUserException extends NotFoundException {
    public NonExistentUserException() {
        this("User doe not exist");
    }

    public NonExistentUserException(String message) {
        super(message);
    }
}
