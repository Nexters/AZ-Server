package org.nexters.az.post.repository;

import org.nexters.az.common.repository.ExtendRepository;
import org.nexters.az.post.entity.PostLike;

public interface PostLikeRepository extends ExtendRepository<PostLike> {
    boolean existsByUserIdAndPostId(Long userId, Long postId);

    int countPostLikesByPostId(Long postId);
}
