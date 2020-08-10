package org.nexters.az.comment.service;

import lombok.RequiredArgsConstructor;
import org.nexters.az.comment.entity.Comment;
import org.nexters.az.comment.exception.NoPermissionAccessCommentException;
import org.nexters.az.comment.exception.NonExistentCommentException;
import org.nexters.az.comment.repository.CommentRepository;
import org.nexters.az.post.entity.Post;
import org.nexters.az.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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

    public void deleteComment(Long deleterId, Long postId, Long commentId) {
        Comment commentForDelete = commentRepository.findByPostIdAndId(postId, commentId).orElseThrow(NonExistentCommentException::new);
        checkWriter(deleterId, commentForDelete.getWriter().getId());
        commentRepository.delete(commentForDelete);
    }

    public Comment modifyComment(User modifier, Long postId, Long commentId, Comment modifyComment){
        Comment commentForModify = commentRepository.findByPostIdAndId(postId, commentId).orElseThrow(NonExistentCommentException::new);
        checkWriter(modifier.getId(), commentForModify.getWriter().getId());
        commentForModify.modifyComment(modifyComment.getComment());
        return commentRepository.save(commentForModify);
    }

    public Page<Comment> getCommentsByWriter(Long writerId, Pageable pageable){
        return commentRepository.findAllByWriterId(writerId, pageable);
    }

    public int findCommentCount(Long postId) {
        return commentRepository.countAllByPostId(postId);
    }

    private void checkWriter(Long accessId, Long writerId) {
        if (accessId.equals(writerId))
            return;
        throw new NoPermissionAccessCommentException();
    }
}
