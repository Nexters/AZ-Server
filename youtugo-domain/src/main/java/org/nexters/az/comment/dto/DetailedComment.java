package org.nexters.az.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.nexters.az.comment.entity.Comment;
import org.nexters.az.user.dto.SimpleUser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class DetailedComment {
    private Long id;
    private Long postId;
    private SimpleUser writer;
    private String content;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private LocalDate createdDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private LocalDate modifiedDate;

    public DetailedComment(Comment comment){
        this.id = comment.getId();
        this.createdDate = comment.getCreatedDate().toLocalDate();
        this.modifiedDate = comment.getModifiedDate().toLocalDate();
        this.postId = comment.getPost().getId();
        this.writer = SimpleUser.of(comment.getWriter());
        this.content = comment.getComment();
    }

    public static DetailedComment detailedCommentOf(Comment comment) {
        return new DetailedComment(comment);
    }

    public static List<DetailedComment> detailedCommentsOf(List<Comment> comments) {
        List<DetailedComment> detailedComments = new ArrayList<>();
        comments.forEach(comment -> detailedComments.add(detailedCommentOf(comment)));

        return detailedComments;
    }
}
