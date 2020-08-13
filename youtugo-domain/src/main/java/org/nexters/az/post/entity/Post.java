package org.nexters.az.post.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.nexters.az.common.entity.BaseTime;
import org.nexters.az.user.entity.User;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "posts")
public class Post extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Column(nullable = false)
    private String content;

    private int likeCount;
    private int viewCount;

    @Builder
    public Post(User author, String content, int likeCount, int viewCount) {
        this.author = author;
        this.content = content;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
    }
}
