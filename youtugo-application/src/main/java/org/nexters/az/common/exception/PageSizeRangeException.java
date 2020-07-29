package org.nexters.az.common.exception;

import org.nexters.az.exception.BadRequestException;

public class PageSizeRangeException extends BadRequestException {
    public PageSizeRangeException() {
        this("Page size must be greater than 1.");
    }

    public PageSizeRangeException(String message) {
        super(message);
    }
}
