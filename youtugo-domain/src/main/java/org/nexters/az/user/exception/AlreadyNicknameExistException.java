package org.nexters.az.user.exception;

import org.nexters.az.exception.ConflictException;

public class AlreadyNicknameExistException extends ConflictException {
    public AlreadyNicknameExistException() {
        this("already user nickname is exist");
    }

    public AlreadyNicknameExistException(String message) {
        super(message);
    }
}
