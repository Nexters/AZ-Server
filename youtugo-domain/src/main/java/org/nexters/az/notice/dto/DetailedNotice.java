package org.nexters.az.notice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.nexters.az.notice.entity.NoticeType;
import org.nexters.az.post.dto.DetailedPost;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class DetailedNotice {
    private DetailedPost detailedPost;
    private Long noticeId;
    private NoticeType noticeType;
    private String message;

    public DetailedNotice(DetailedPost detailedPost, Long noticeId, NoticeType noticeType, String message) {
        this.detailedPost = detailedPost;
        this.noticeId = noticeId;
        this.noticeType = noticeType;
        this.message = message;
    }
}
