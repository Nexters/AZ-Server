package org.nexters.az.comment.service;

import lombok.RequiredArgsConstructor;
import org.nexters.az.comment.entity.Comment;
import org.nexters.az.comment.exception.CommentNotFoundException;
import org.nexters.az.comment.exception.CommentUnauthorizedException;
import org.nexters.az.comment.repository.CommentRepository;
import org.nexters.az.post.entity.Post;
import org.nexters.az.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment create(Comment comment) {
        return commentRepository.save(comment);
    }

    public Page<Comment> getComments(Post post, Pageable pageable) {
        return commentRepository.findAllByPostId(post.getId(), pageable);
    }

    public ResponseEntity deleteComment(User deleter, Post post, Long commentId) {
        Comment comment = commentRepository.findById(commentId, Comment.class).orElseThrow(CommentNotFoundException::new);
        checkWriter(deleter.getId(), comment.getId());
        Comment commentForDelete = commentRepository.findByPostIdAndId(post.getId(),
                commentId).orElseThrow(CommentNotFoundException::new);
        commentRepository.delete(commentForDelete);
        return ResponseEntity.noContent().build();
    }

    private void checkWriter(Long deleterId, Long writerId) {
        if (deleterId.equals(writerId))
            return;
        throw new CommentUnauthorizedException();
    }
}
