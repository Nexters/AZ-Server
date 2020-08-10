package org.nexters.az.comment.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.nexters.az.comment.entity.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class DetailedComment {
    private Long id;
    private Long postId;
    private String writer;
    private String content;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdDate;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime modifiedDate;

    public DetailedComment(Comment comment){
        this.id=comment.getId();
        this.createdDate=comment.getCreatedDate();
        this.modifiedDate=comment.getModifiedDate();
        this.postId=comment.getPost().getId();
        this.writer=comment.getWriter().getNickname();
        this.content=comment.getComment();
    }

    public static DetailedComment detailedCommentOf(Comment comment) {
        return new DetailedComment(comment);
    }

    public static List<DetailedComment> detailedCommentsOf(List<Comment> comments) {
        List<DetailedComment> detailedComments = new ArrayList<>();
        comments.forEach(comment -> {
            detailedComments.add(detailedCommentOf(comment));
        });

        return detailedComments;
    }
}
