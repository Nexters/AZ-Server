package org.nexters.az.notice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.nexters.az.notice.entity.Notice;
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

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private LocalDate createdDate;

    public DetailedNotice(DetailedPost detailedPost, Notice notice, String message) {
        this.detailedPost = detailedPost;
        this.noticeId = notice.getId();
        this.createdDate = notice.getCreatedDate().toLocalDate();
        this.noticeType = notice.getNoticeType();
        this.message = message;
    }
}
