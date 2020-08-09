package org.nexters.az.post.repository;

import org.nexters.az.common.repository.ExtendRepository;
import org.nexters.az.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends ExtendRepository<Post> {
    void deleteByIdAndAuthorId(Long postId, Long userId);

    Page<Post> findAllByAuthorId (Long authorId, Pageable pageable);

    boolean existsById(Long postId);

    int countAllByAuthorId(Long authorId);
}
