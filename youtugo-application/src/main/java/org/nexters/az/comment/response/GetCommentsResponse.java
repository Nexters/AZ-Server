package org.nexters.az.comment.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.nexters.az.comment.dto.DetailedComment;
import org.nexters.az.comment.entity.Comment;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetCommentsResponse {
    private List<DetailedComment> commentList;
}
