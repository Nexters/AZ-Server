package org.nexters.az.comment.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.nexters.az.comment.entity.Comment;

@Getter
@AllArgsConstructor
public class ModifyCommentRequest {
    private String comment;

    public static Comment toEntity(ModifyCommentRequest modifyCommentRequest){
        return Comment.builder().comment(modifyCommentRequest.getComment()).build();
    }
}
