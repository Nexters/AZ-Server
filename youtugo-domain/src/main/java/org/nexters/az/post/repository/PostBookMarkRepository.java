package org.nexters.az.post.repository;

import org.nexters.az.common.repository.ExtendRepository;
import org.nexters.az.post.entity.PostBookMark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface PostBookMarkRepository extends ExtendRepository<PostBookMark> {

    Boolean existsByUserIdAndPostId(Long userId, Long PostId);

    int countPostBookMarksByPostId(Long postId);

    Optional<PostBookMark> findByUserIdAndPostId(Long userId, Long postId);

    Page<PostBookMark> findAllByUserId(Long userId, Pageable pageable);
}
