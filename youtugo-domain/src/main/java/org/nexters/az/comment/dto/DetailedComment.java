package org.nexters.az.comment.dto;

import lombok.Getter;
import org.nexters.az.comment.entity.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class DetailedComment {
    private Long id;
    private LocalDateTime created_At;
    private LocalDateTime modify_At;
    private Long postId;
    private String writer;
    private String content;

    public DetailedComment(Comment comment){
        this.id=comment.getId();
        this.created_At=comment.getCreatedDate();
        this.modify_At=comment.getModifiedDate();
        this.postId=comment.getPost().getId();
        this.writer=comment.getWriter().getNickname();
        this.content=comment.getComment();
    }

    public static List<DetailedComment> detailedCommentsOf(List<Comment> comments) {
        List<DetailedComment> detailedComments = new ArrayList<>();
        comments.forEach(comment -> {
            detailedComments.add(detailedCommentOf(comment));
        });

        return detailedComments;
    }

    public static DetailedComment detailedCommentOf(Comment comment) {
        return new DetailedComment(comment);
    }

}
