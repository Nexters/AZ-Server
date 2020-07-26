package org.nexters.az.comment.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.nexters.az.comment.dto.DetailedComment;
import org.nexters.az.comment.entity.Comment;
import org.nexters.az.comment.exception.DeleterNotFoundException;
import org.nexters.az.comment.request.WriteCommentRequest;
import org.nexters.az.comment.response.GetCommentsResponse;
import org.nexters.az.comment.response.WriteCommentResponse;
import org.nexters.az.comment.service.CommentService;
import org.nexters.az.post.entity.Post;
import org.nexters.az.post.repository.PostRepository;
import org.nexters.az.user.entity.User;
import org.nexters.az.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/posts")
public class CommentController {
    private final CommentService commentService;

    // TODO 4: 추후 [TODO 3]이 구현됨에 따라 삭제될 예정
    private final PostRepository postRepository;

    // TODO 2: 추후 [TODO 1] 이 구현됨에 따라 삭제될 예정
    private final UserRepository userRepository;

    @ApiOperation("댓글작성")
    @PostMapping("/{postId}/comments/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public WriteCommentResponse write(@PathVariable Long postId,
                                      WriteCommentRequest writeCommentRequest) {
        // TODO 1: 추후 accessToken으로 댓글 작성자 찾기(아래 코드는 가데이터)
        User user = User.builder()
                .identification("test")
                .nickname("test")
                .hashedPassword("test")
                .build();
        User writer = userRepository.save(user);

        // TODO 3: 추후 PostService로 댓글에 대한 게시글 찾기
        Post post = postRepository.findById(postId, Post.class).orElseThrow(() -> new NoSuchElementException("해당 게시글이 없습니다"));

        DetailedComment detailedComment = detailedCommentOf(
                commentService.create(Comment.builder()
                        .post(post)
                        .writer(writer)
                        .comment(writeCommentRequest.getComment())
                        .build()));

        return new WriteCommentResponse(detailedComment);
    }

    @ApiOperation("해당 게시글에 대한 댓글들 불러오기")
    @GetMapping("/{postId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public GetCommentsResponse getComments(@PathVariable Long postId) {

        // TODO 3: 추후 PostService로 댓글에 대한 게시글 찾기
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("해당 게시글이 없습니다"));

        return new GetCommentsResponse(detailedCommentsOf(commentService.getComments(post)));
    }

    private List<DetailedComment> detailedCommentsOf(List<Comment> comments) {
        List<DetailedComment> detailedComments = new ArrayList<>();
        comments.forEach(comment -> {
            detailedComments.add(detailedCommentOf(comment));
        });

        return detailedComments;
    }

    private DetailedComment detailedCommentOf(Comment comment) {
        return new DetailedComment(comment);
    }

    @ApiOperation("댓글 삭제")
    @DeleteMapping("/{postId}/comments/{commentId}")
    public void delete(@PathVariable Long postId,
                       @PathVariable Long commentId) {

        Comment comment = commentService.getComment(commentId);

        // TODO 1: 추후 accessToken으로 댓글 삭제하려는 사용자 찾기(아래 코드는 가데이터)
        User user = User.builder()
                .identification("test")
                .nickname("test")
                .hashedPassword("test")
                .build();
        User deleter = userRepository.save(user);

        checkWriter(deleter, comment.getWriter());

        // TODO 3: 추후 PostService로 댓글에 대한 게시글 찾기
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("해당 게시글이 없습니다"));

        commentService.delete(post, commentId);

    }

    private void checkWriter(User deleter, User writer) {
        if (deleter.getId().equals(writer.getId()))
            return;
        throw new DeleterNotFoundException();
    }
}
