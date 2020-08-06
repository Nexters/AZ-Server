package org.nexters.az.post.exception;

import org.nexters.az.exception.NotFoundException;

public class NonExistentBookMarkException extends NotFoundException {
    public NonExistentBookMarkException() {
        this("Post does not exist");
    }
    public NonExistentBookMarkException(String message) {super(message);}
}
