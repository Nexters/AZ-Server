package org.nexters.az.comment.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.nexters.az.comment.dto.DetailedComment;

@Getter
@AllArgsConstructor
public class WriteCommentResponse {
    private DetailedComment detailedComment;

}
