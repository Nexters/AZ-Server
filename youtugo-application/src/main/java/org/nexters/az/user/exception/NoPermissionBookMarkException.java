package org.nexters.az.user.exception;

import org.nexters.az.exception.UnauthorizedException;

public class NoPermissionBookMarkException extends UnauthorizedException {

    public NoPermissionBookMarkException() {
        this("User does not have insert bookmark permission");
    }

    public NoPermissionBookMarkException(String message) {
        super(message);
    }
}
