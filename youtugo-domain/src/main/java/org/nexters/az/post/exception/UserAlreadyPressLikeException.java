package org.nexters.az.post.exception;

import org.nexters.az.exception.UnauthorizedException;

public class UserAlreadyPressLikeException extends UnauthorizedException {
    public UserAlreadyPressLikeException() {
        this("User already press like");
    }

    public UserAlreadyPressLikeException(String message) {
        super(message);
    }
}
