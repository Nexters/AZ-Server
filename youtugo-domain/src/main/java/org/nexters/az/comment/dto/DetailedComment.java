package org.nexters.az.comment.dto;

import lombok.Getter;
import org.nexters.az.comment.entity.Comment;

import java.time.LocalDateTime;

@Getter
public class DetailedComment {
    private Long id;
    private LocalDateTime created_at;
    private LocalDateTime modify_at;
    private Long postId;
    private String writer;
    private String content;

    public DetailedComment(Comment comment){
        this.id=comment.getId();
        this.created_at=comment.getCreatedDate();
        this.modify_at=comment.getModifiedDate();
        this.postId=comment.getPost().getId();
        this.writer=comment.getWriter().getNickname();
        this.content=comment.getComment();
    }
}
