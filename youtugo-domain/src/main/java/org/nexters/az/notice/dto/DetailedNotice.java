package org.nexters.az.notice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.nexters.az.notice.entity.NoticeType;

@Getter
@NoArgsConstructor
public class DetailedNotice {
    private Long postId;
    private Long noticeId;
    private NoticeType noticeType;
    private String message;

    public DetailedNotice(Long noticeId, Long postId, NoticeType noticeType, String message) {
        this.postId = postId;
        this.noticeId = noticeId;
        this.noticeType = noticeType;
        this.message = message;
    }
}
