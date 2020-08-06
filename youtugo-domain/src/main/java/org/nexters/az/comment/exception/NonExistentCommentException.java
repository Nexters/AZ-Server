package org.nexters.az.comment.exception;

import org.nexters.az.exception.NotFoundException;

public class NonExistentCommentException extends NotFoundException {
    public NonExistentCommentException() {
        this("Comment does not exist");
    }

    public NonExistentCommentException(String message) {
        super(message);
    }
}
