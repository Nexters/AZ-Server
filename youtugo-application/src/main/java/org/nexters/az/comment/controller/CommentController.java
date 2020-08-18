package org.nexters.az.comment.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.nexters.az.auth.security.TokenSubject;
import org.nexters.az.auth.service.AuthService;
import org.nexters.az.comment.dto.DetailedComment;
import org.nexters.az.comment.entity.Comment;
import org.nexters.az.comment.request.ModifyCommentRequest;
import org.nexters.az.comment.request.WriteCommentRequest;
import org.nexters.az.comment.response.GetCommentsResponse;
import org.nexters.az.comment.response.ModifyCommentResponse;
import org.nexters.az.comment.response.WriteCommentResponse;
import org.nexters.az.comment.service.CommentService;
import org.nexters.az.common.dto.CurrentPageAndPageSize;
import org.nexters.az.common.dto.SimplePage;
import org.nexters.az.common.validation.PageValidation;
import org.nexters.az.notice.entity.Notice;
import org.nexters.az.notice.entity.NoticeType;
import org.nexters.az.notice.service.NoticeService;
import org.nexters.az.post.entity.Post;
import org.nexters.az.post.service.PostService;
import org.nexters.az.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/posts")
public class CommentController {
    private final CommentService commentService;
    private final AuthService authService;
    private final PostService postService;
    private final NoticeService noticeService;

    @ApiOperation("댓글 작성")
    @PostMapping("/{postId}/comments/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public WriteCommentResponse writeComment(@PathVariable Long postId,
                                             @RequestHeader String accessToken,
                                             @RequestBody WriteCommentRequest writeCommentRequest) {

        User writer = authService.findUserByToken(accessToken, TokenSubject.ACCESS_TOKEN);

        Post post = postService.getPost(postId);

        DetailedComment detailedComment = DetailedComment.detailedCommentOf(
                commentService.createComment(Comment.builder()
                        .post(post)
                        .writer(writer)
                        .comment(writeCommentRequest.getComment())
                        .build()));

        noticeService.insertNotice(post.getAuthor(),post, NoticeType.COMMENT,writer.getNickname());
        return new WriteCommentResponse(detailedComment);
    }

    @ApiOperation("해당 게시글에 대한 댓글들 조회")
    @GetMapping("/{postId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public GetCommentsResponse getComments(@PathVariable Long postId,
                                           @RequestParam(required = false, defaultValue = "1") int currentPage,
                                           @RequestParam(required = false, defaultValue = "10") int size) {

        Post post = postService.getPost(postId);

        CurrentPageAndPageSize currentPageAndPageSize = PageValidation.getInstance().verify(currentPage, size);

        Page<Comment> searchResult = commentService.getComments(post,
                PageRequest.of(
                        currentPageAndPageSize.getCurrentPage() - 1,
                        currentPageAndPageSize.getPageSize(),
                        Sort.by("createdDate").descending()
                )
        );

        SimplePage simplePage = SimplePage.builder()
                .currentPage(searchResult.getNumber())
                .totalPages(searchResult.getTotalPages())
                .totalElements(searchResult.getTotalElements())
                .build();

        List<DetailedComment> detailedCommentList = DetailedComment.detailedCommentsOf(searchResult.getContent());

        return new GetCommentsResponse(detailedCommentList, simplePage);
    }

    @ApiOperation("댓글 수정")
    @PatchMapping("/{postId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public ModifyCommentResponse modifyComment(@PathVariable Long postId,
                                               @PathVariable Long commentId,
                                               @RequestBody ModifyCommentRequest modifyCommentRequest,
                                               @RequestHeader String accessToken) {

        User modifier = authService.findUserByToken(accessToken, TokenSubject.ACCESS_TOKEN);

        Comment modifyComment = ModifyCommentRequest.toEntity(modifyCommentRequest);
        DetailedComment detailedComment = DetailedComment.detailedCommentOf(commentService.modifyComment(modifier, postId, commentId, modifyComment));
        return new ModifyCommentResponse(detailedComment);
    }

    @ApiOperation("댓글 삭제")
    @DeleteMapping("/{postId}/comments/{commentId}")
    public void deleteComment(@PathVariable Long postId,
                              @PathVariable Long commentId,
                              @RequestHeader String accessToken) {

        User deleter = authService.findUserByToken(accessToken,TokenSubject.ACCESS_TOKEN);
        Long deleterId = deleter.getId();

        commentService.deleteComment(deleterId, postId, commentId);
    }
}
