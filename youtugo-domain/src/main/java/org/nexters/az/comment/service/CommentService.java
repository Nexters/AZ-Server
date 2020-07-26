package org.nexters.az.comment.service;

import lombok.RequiredArgsConstructor;
import org.nexters.az.comment.entity.Comment;
import org.nexters.az.comment.exception.CommentNotFoundException;
import org.nexters.az.comment.repository.CommentRepository;
import org.nexters.az.post.entity.Post;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment create(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> getComments(Post post) {
        return commentRepository.findAllByPost(post);
    }

    public Comment getComment(Long id) {
        return commentRepository.findById(id, Comment.class).orElseThrow(CommentNotFoundException::new);
    }

    public void delete(Post post, Long id) {
        commentRepository.delete(commentRepository.findByPostAndId(post, id));
    }
}
