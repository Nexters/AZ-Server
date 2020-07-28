package org.nexters.az.post.dto;

import lombok.Getter;
import org.nexters.az.post.entity.Post;

import java.time.LocalDateTime;

@Getter
public class DetailedPost {
    private Long id;
    private String authorNickname;
    private String content;
    private int likes;
    private int bookMarks;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public DetailedPost(Post post, int likes, int bookMarks) {
        this.id = post.getId();
        this.authorNickname = post.getAuthor().getNickname();
        this.content = post.getContent();
        this.likes = likes;
        this.bookMarks = bookMarks;
        this.createdDate = post.getCreatedDate();
        this.modifiedDate = post.getModifiedDate();
    }
}
