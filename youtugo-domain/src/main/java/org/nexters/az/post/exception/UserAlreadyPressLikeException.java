package org.nexters.az.post.exception;

import org.nexters.az.exception.ConflictException;

public class UserAlreadyPressLikeException extends ConflictException {
    public UserAlreadyPressLikeException() {
        this("User already press like");
    }

    public UserAlreadyPressLikeException(String message) {
        super(message);
    }
}
