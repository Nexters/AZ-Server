package org.nexters.az.notice.entity;

import lombok.Getter;

@Getter
public enum NoticeType {
    COMMENT("님이 회원님의 개그에 댓글을 남겼습니다."),
    LIKE("님이 회원님의 개그를 좋아합니다.");

    private String message;

    NoticeType(String message) {
        this.message = message;
    }
}
