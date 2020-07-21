package org.nexters.az.exception;

public class ConflictException extends CanHaveStatusException {
    public ConflictException() {
        this("Conflict Exception");
    }

    public ConflictException(String message){
        super(409, message);
    }
}