package org.nexters.az.post.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.nexters.az.post.dto.DetailedPost;

@AllArgsConstructor
@Getter
public class GetPostResponse {
    private DetailedPost detailedPost;
}
