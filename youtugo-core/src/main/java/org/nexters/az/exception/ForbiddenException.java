package org.nexters.az.exception;

public class ForbiddenException extends CanHaveStatusException {

    public ForbiddenException(){
        this("Forbidden Exception");
    }
    public ForbiddenException(String message) {
        super(403, message);
    }
}
