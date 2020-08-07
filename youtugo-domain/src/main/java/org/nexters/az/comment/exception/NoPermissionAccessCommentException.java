package org.nexters.az.comment.exception;

import org.nexters.az.exception.ForbiddenException;

public class NoPermissionAccessCommentException extends ForbiddenException {
    public NoPermissionAccessCommentException(){this("User doest not have access comment permission");}

    public NoPermissionAccessCommentException(String message) {
        super(message);
    }
}
