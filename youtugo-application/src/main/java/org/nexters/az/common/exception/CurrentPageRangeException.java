package org.nexters.az.common.exception;

import org.nexters.az.exception.BadRequestException;

public class CurrentPageRangeException extends BadRequestException {
    public CurrentPageRangeException() {
        this("Current page must be greater than 1.");
    }

    public CurrentPageRangeException(String message) {
        super(message);
    }
}
