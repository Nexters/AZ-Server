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
    private int comments;
    private boolean isPressLike;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public DetailedPost(Post post) {
        this.id = post.getId();
        this.authorNickname = post.getAuthor().getNickname();
        this.content = post.getContent();
        this.createdDate = post.getCreatedDate();
        this.modifiedDate = post.getModifiedDate();
        this.isPressLike = false;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setBookMarks(int bookMarks) {
        this.bookMarks = bookMarks;
    }

    public void setIsPressLike(boolean isPressLike) {
        this.isPressLike = isPressLike;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }
}
