package org.nexters.az.notice.exception;

import org.nexters.az.exception.ForbiddenException;

public class NoPermissionAccessNoticeException extends ForbiddenException {
    public NoPermissionAccessNoticeException() {
        this("User doest not have delete Notice permission");
    }
    public NoPermissionAccessNoticeException(String message) {super(message);}
}
