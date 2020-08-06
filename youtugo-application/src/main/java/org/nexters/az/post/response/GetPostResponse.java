package org.nexters.az.post.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.nexters.az.post.dto.DetailedPost;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetPostResponse {
    private DetailedPost detailedPost;
}
