package org.nexters.az.post.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.nexters.az.common.dto.SimplePage;
import org.nexters.az.post.dto.DetailedPost;

import java.util.List;

@AllArgsConstructor
@Getter
public class SearchPostsResponse {
    private List<DetailedPost> posts;
    private SimplePage simplePage;
}
