package org.nexters.az.comment.exception;

import org.nexters.az.exception.UnauthorizedException;

public class CommentUnauthorizedException extends UnauthorizedException {
    public CommentUnauthorizedException(){super("댓글 삭제 권한이 없습니다");}

}
