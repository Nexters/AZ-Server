package org.nexters.az.user.exception;

import org.nexters.az.exception.ForbiddenException;

public class NoPermissionNoticeException  extends ForbiddenException {

    public NoPermissionNoticeException() {
        this("User does not have access notice permission");
    }

    public NoPermissionNoticeException(String message) {
        super(message);
    }
}
