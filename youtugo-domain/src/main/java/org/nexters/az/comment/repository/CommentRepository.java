package org.nexters.az.comment.repository;

import org.nexters.az.comment.entity.Comment;
import org.nexters.az.common.repository.ExtendRepository;
import org.nexters.az.post.entity.Post;

import java.util.List;

public interface CommentRepository extends ExtendRepository<Comment> {
    List<Comment> findAllByPost(Post post);

    Comment findByPostAndId(Post post, Long id);
}
