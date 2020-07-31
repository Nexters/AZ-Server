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

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Page<Comment> getComments(Post post, Pageable pageable) {
        return commentRepository.findAllByPostId(post.getId(), pageable);
    }

    public ResponseEntity deleteComment(User deleter, Long commentId) {
        Comment commentForDelete = commentRepository.findById(commentId, Comment.class).orElseThrow(CommentNotFoundException::new);
        checkWriter(deleter.getId(), commentForDelete.getId());
        commentRepository.delete(commentForDelete);
        return ResponseEntity.noContent().build();
    }

    public Comment modifyComment(User modifier, Long commentId, Comment modifyComment){
        Comment commentForModify = commentRepository.findById(commentId, Comment.class).orElseThrow(CommentNotFoundException::new);
        checkWriter(modifier.getId(), commentForModify.getId());
        commentForModify.modifyComment(modifyComment.getComment());
        return commentRepository.save(commentForModify);
    }

    private void checkWriter(Long accessId, Long writerId) {
        if (accessId.equals(writerId))
            return;
        throw new CommentUnauthorizedException();
    }
}
