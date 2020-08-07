package org.nexters.az.comment.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.nexters.az.common.entity.BaseTime;
import org.nexters.az.post.entity.Post;
import org.nexters.az.user.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comments")
public class Comment extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private User writer;

    @Column(nullable = false)
    private String comment;

    @Builder
    public Comment(Post post, User writer, String comment) {
        this.post = post;
        this.writer = writer;
        this.comment = comment;
    }

    public void modifyComment(String comment) {
        this.comment = comment;
    }
}
