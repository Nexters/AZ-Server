package org.nexters.az.notice.exception;

import org.nexters.az.exception.NotFoundException;

public class NotExistentNoticeException extends NotFoundException {
    public NotExistentNoticeException() {
        this("Notice does not exist");
    }
    public NotExistentNoticeException(String message) {super(message);}
}
