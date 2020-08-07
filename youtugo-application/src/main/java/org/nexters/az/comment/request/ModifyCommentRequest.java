package org.nexters.az.comment.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.nexters.az.comment.entity.Comment;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ModifyCommentRequest {
    private String comment;

    public static Comment toEntity(ModifyCommentRequest modifyCommentRequest){
        return Comment.builder().comment(modifyCommentRequest.getComment()).build();
    }
}
