package org.nexters.az.comment.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.nexters.az.comment.dto.DetailedComment;
import org.nexters.az.comment.entity.Comment;
import org.nexters.az.comment.request.ModifyCommentRequest;
import org.nexters.az.comment.request.WriteCommentRequest;
import org.nexters.az.comment.response.GetCommentsResponse;
import org.nexters.az.comment.response.ModifyCommentResponse;
import org.nexters.az.comment.response.WriteCommentResponse;
import org.nexters.az.comment.service.CommentService;
import org.nexters.az.common.dto.SimplePage;
import org.nexters.az.post.entity.Post;
import org.nexters.az.post.repository.PostRepository;
import org.nexters.az.user.entity.User;
import org.nexters.az.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("댓글 작성")
    @PostMapping("/{postId}/comments/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public WriteCommentResponse writeComment(@PathVariable Long postId,
                                             @RequestHeader String accessToken,
                                             @RequestBody WriteCommentRequest writeCommentRequest) {

        // TODO 1: 추후 accessToken으로 댓글 작성자 찾기(아래 코드는 가데이터)
        User user = User.builder()
                .identification("test")
                .nickname("test")
                .hashedPassword("test")
                .build();
        User writer = userRepository.save(user);

        // TODO 3: 추후 PostService로 댓글에 대한 게시글 찾기
        Post post = postRepository.findById(postId, Post.class).orElseThrow(() -> new NoSuchElementException("해당 게시글이 없습니다"));

        DetailedComment detailedComment = DetailedComment.detailedCommentOf(
                commentService.createComment(Comment.builder()
                        .post(post)
                        .writer(writer)
                        .comment(writeCommentRequest.getComment())
                        .build()));

        return new WriteCommentResponse(detailedComment);
    }

    @ApiOperation("해당 게시글에 대한 댓글들 조회")
    @GetMapping("/{postId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public GetCommentsResponse getComments(@PathVariable Long postId,
                                           @RequestHeader String accessToken,
                                           @RequestParam(required = false, defaultValue = "0") int currentPage,
                                           @RequestParam(required = false, defaultValue = "10") int size) {

        // TODO 3: 추후 PostService로 댓글에 대한 게시글 찾기
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("해당 게시글이 없습니다"));

        Page<Comment> commentList = commentService.getComments(post, PageRequest.of(currentPage, size, Sort.by("createdDate").descending()));
        SimplePage simplePage = SimplePage.builder()
                .currentPage(commentList.getNumber())
                .totalPages(commentList.getTotalPages())
                .totalElements(commentList.getTotalElements())
                .build();

        List<DetailedComment> detailedCommentList = DetailedComment.detailedCommentsOf(commentList.getContent());

        return new GetCommentsResponse(detailedCommentList, simplePage);
    }

    @ApiOperation("댓글 수정")
    @PatchMapping("/{postId}/comments/{commentId}")
    public ModifyCommentResponse modifyComment(@PathVariable Long postId,
                                               @PathVariable Long commentId,
                                               @RequestBody ModifyCommentRequest modifyCommentRequest,
                                               @RequestHeader String accessToken){

        // TODO 1: 추후 accessToken으로 댓글 삭제하려는 사용자 찾기(아래 코드는 가데이터)
        User user = User.builder()
                .identification("test")
                .nickname("test")
                .hashedPassword("test")
                .build();
        User modifier = userRepository.save(user);

        // TODO 3: 추후 PostService로 댓글에 대한 게시글 찾기
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("해당 게시글이 없습니다"));

        Comment modifyComment = ModifyCommentRequest.toEntity(modifyCommentRequest);
        DetailedComment detailedComment = DetailedComment.detailedCommentOf(commentService.modifyComment(modifier, commentId, modifyComment));
        return new ModifyCommentResponse(detailedComment);
    }

    @ApiOperation("댓글 삭제")
    @DeleteMapping("/{postId}/comments/{commentId}")
    public void deleteComment(@PathVariable Long postId,
                                 @PathVariable Long commentId,
                                 @RequestHeader String accessToken) {

        // TODO 1: 추후 accessToken으로 댓글 삭제하려는 사용자 찾기(아래 코드는 가데이터)
        User user = User.builder()
                .identification("test")
                .nickname("test")
                .hashedPassword("test")
                .build();
        User deleter = userRepository.save(user);

        // TODO 3: 추후 PostService로 댓글에 대한 게시글 찾기
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("해당 게시글이 없습니다"));
        commentService.deleteComment(deleter, commentId);
    }
}
