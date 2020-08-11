package org.nexters.az.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.nexters.az.post.entity.Post;
import org.nexters.az.user.dto.SimpleUser;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class DetailedPost {
    private Long id;
    private SimpleUser author;
    private String content;
    private int likes;
    private int bookMarkCount;
    private int commentCount;
    private boolean pressLike;
    private boolean pressBookMark;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private LocalDate createdDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private LocalDate modifiedDate;

    public DetailedPost(Post post) {
        this.id = post.getId();
        this.author = SimpleUser.of(post.getAuthor());
        this.content = post.getContent();
        this.createdDate = post.getCreatedDate().toLocalDate();
        this.modifiedDate = post.getModifiedDate().toLocalDate();
        this.pressLike = false;
        this.pressBookMark = false;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setBookMarkCount(int bookMarkCount) {
        this.bookMarkCount = bookMarkCount;
    }

    public void setPressLike(boolean pressLike) {
        this.pressLike = pressLike;
    }

    public void setPressBookMark(boolean pressBookMark) {
        this.pressBookMark = pressBookMark;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public void makeSimpleContent() {
        content = content.replace('\n', ' ').replaceAll("( )+", " ");
    }
}
