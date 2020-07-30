package org.nexters.az.post.exception;

import org.nexters.az.exception.UnauthorizedException;

public class NoPermissionDeletePostException extends UnauthorizedException {
    public NoPermissionDeletePostException() {
        this("User doest not have delete post permission");
    }

    public NoPermissionDeletePostException(String message) {
        super(message);
    }
}
