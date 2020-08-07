package org.nexters.az.user.exception;

import org.nexters.az.exception.ForbiddenException;
import org.nexters.az.exception.UnauthorizedException;

public class NoPermissionBookMarkException extends ForbiddenException {

    public NoPermissionBookMarkException() {
        this("User does not have access bookmark permission");
    }

    public NoPermissionBookMarkException(String message) {
        super(message);
    }
}
