package org.nexters.az.exception;

public class BadRequestException extends CanHaveStatusException {
    public BadRequestException() {
        this("Bad Request Exception");
    }
    public BadRequestException(String message){
        super(400, message);
    }
}