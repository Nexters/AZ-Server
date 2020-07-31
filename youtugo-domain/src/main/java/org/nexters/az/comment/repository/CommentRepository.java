package org.nexters.az.comment.repository;

import org.nexters.az.comment.entity.Comment;
import org.nexters.az.common.repository.ExtendRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface CommentRepository extends ExtendRepository<Comment> {
    Page<Comment> findAllByPostId(Long postId, Pageable pageable);
}
