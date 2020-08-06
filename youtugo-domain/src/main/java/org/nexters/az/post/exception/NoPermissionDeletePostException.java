package org.nexters.az.post.exception;

import org.nexters.az.exception.ForbiddenException;

public class NoPermissionDeletePostException extends ForbiddenException {
    public NoPermissionDeletePostException() {
        this("User doest not have delete post permission");
    }

    public NoPermissionDeletePostException(String message) {
        super(message);
    }
}
