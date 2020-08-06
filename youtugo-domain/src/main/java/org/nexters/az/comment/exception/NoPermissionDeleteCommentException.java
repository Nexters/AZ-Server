package org.nexters.az.comment.exception;

import org.nexters.az.exception.ForbiddenException;

public class NoPermissionDeleteCommentException extends ForbiddenException {
    public NoPermissionDeleteCommentException(){this("User doest not have delete comment permission");}

    public NoPermissionDeleteCommentException(String message) {
        super(message);
    }
}
