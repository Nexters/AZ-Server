package org.nexters.az.post.dto;

import lombok.Getter;
import org.nexters.az.post.entity.Post;

@Getter
public class DetailedPost {
    private Long id;
    private String authorNickname;
    private String content;
    private int likes;
    private int bookMarks;

    public DetailedPost(Post post, int likes, int bookMarks) {
        this.id = post.getId();
        this.authorNickname = post.getAuthor().getNickname();
        this.content = post.getContent();
        this.likes = likes;
        this.bookMarks = bookMarks;
    }
}
