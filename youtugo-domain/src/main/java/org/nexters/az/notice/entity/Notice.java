package org.nexters.az.notice.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.nexters.az.common.entity.BaseTime;
import org.nexters.az.post.entity.Post;
import org.nexters.az.user.entity.User;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "notices")
public class Notice extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private NoticeType noticeType;

    @Column(nullable = false)
    private String nickName;

    @Builder
    public Notice(User user, Post post, NoticeType noticeType, String nickName) {
        this.user = user;
        this.post = post;
        this.noticeType = noticeType;
        this.nickName = nickName;
    }
}
