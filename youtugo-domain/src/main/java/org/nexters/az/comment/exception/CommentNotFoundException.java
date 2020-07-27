package org.nexters.az.comment.exception;

import org.nexters.az.exception.NotFoundException;

public class CommentNotFoundException extends NotFoundException {
    public CommentNotFoundException() {
        super("해당 댓글을 찾을 수 없습니다.");
    }
}
