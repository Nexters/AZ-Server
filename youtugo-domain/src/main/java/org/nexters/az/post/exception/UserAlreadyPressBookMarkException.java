package org.nexters.az.post.exception;

import org.nexters.az.exception.ConflictException;
import org.nexters.az.exception.UnauthorizedException;

public class UserAlreadyPressBookMarkException extends ConflictException {
    public UserAlreadyPressBookMarkException() {
        this("User already press BookMark");
    }

    public UserAlreadyPressBookMarkException(String message) {
        super(message);
    }
}
