package org.nexters.az.exception;

public abstract class CanHaveStatusException extends RuntimeException {
    private int status;
    private String message;

    public CanHaveStatusException(int status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
