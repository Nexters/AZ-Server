package org.nexters.az.user.exception;

import org.nexters.az.exception.ConflictException;

public class AlreadyIdentificationExistException extends ConflictException {
    public AlreadyIdentificationExistException() {
        this("already user identification is exist");
    }

    public AlreadyIdentificationExistException(String message) {
        super(message);
    }
}
