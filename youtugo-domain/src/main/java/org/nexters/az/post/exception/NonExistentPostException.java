package org.nexters.az.post.exception;

import org.nexters.az.exception.NotFoundException;

public class NonExistentPostException extends NotFoundException {
    public NonExistentPostException() {
        this("Post does not exist");
    }

    public NonExistentPostException(String message) {
        super(message);
    }
}
