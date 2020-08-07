package org.nexters.az.post.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.nexters.az.post.entity.Post;
import org.nexters.az.user.dto.SimpleUser;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class DetailedPost {
    private Long id;
    private SimpleUser author;
    private String content;
    private int likes;
    private int bookMarks;
    private int comments;
    private boolean pressLike;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdDate;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime modifiedDate;

    public DetailedPost(Post post) {
        this.id = post.getId();
        this.author = SimpleUser.of(post.getAuthor());
        this.content = post.getContent();
        this.createdDate = post.getCreatedDate();
        this.modifiedDate = post.getModifiedDate();
        this.pressLike = false;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setBookMarks(int bookMarks) {
        this.bookMarks = bookMarks;
    }

    public void setPressLike(boolean pressLike) {
        this.pressLike = pressLike;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public void makeSimpleContent() {
        content = content.replace('\n', ' ').replaceAll("( )+", " ");
    }
}
