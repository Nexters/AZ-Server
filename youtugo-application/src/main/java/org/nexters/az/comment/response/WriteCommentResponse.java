package org.nexters.az.comment.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.nexters.az.comment.dto.DetailedComment;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WriteCommentResponse {
    private DetailedComment detailedComment;

}
