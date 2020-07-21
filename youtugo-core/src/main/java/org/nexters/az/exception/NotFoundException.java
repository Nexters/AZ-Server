package org.nexters.az.exception;

public class NotFoundException extends CanHaveStatusException {

    public NotFoundException(){
        this("NotFound Exception");
    }
    public NotFoundException(String message) {
        super(404, message);
    }
}
