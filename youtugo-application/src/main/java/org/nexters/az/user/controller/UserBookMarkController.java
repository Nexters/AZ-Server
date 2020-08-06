package org.nexters.az.user.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.nexters.az.auth.security.TokenSubject;
import org.nexters.az.auth.service.AuthService;
import org.nexters.az.comment.service.CommentService;
import org.nexters.az.common.dto.CurrentPageAndPageSize;
import org.nexters.az.common.validation.PageValidation;
import org.nexters.az.post.dto.DetailedPost;
import org.nexters.az.post.entity.Post;
import org.nexters.az.post.entity.PostBookMark;
import org.nexters.az.post.response.GetPostResponse;
import org.nexters.az.post.response.GetPostsResponse;
import org.nexters.az.post.service.PostBookMarkService;
import org.nexters.az.post.service.PostService;
import org.nexters.az.user.entity.User;
import org.nexters.az.user.exception.NoPermissionBookMarkException;
import org.nexters.az.user.response.DeleteBookmarkPostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/users")
public class UserBookMarkController {

    private final AuthService authService;
    private final CommentService commentService;
    private final PostService postService;
    private final PostBookMarkService postBookMarkService;

    @ApiOperation("게시글 북마크 조회")
    @GetMapping("/{userId}/bookmark/posts)")
    @ResponseStatus(HttpStatus.OK)
    public GetPostsResponse getBookmarkPosts(@RequestHeader String accessToken, @PathVariable Long userId,
                                             @RequestParam(required = false, defaultValue = "1") int currentPage,
                                             @RequestParam(required = false, defaultValue = "10") int size) {

        CurrentPageAndPageSize currentPageAndPageSize = PageValidation.getInstance().verify(currentPage, size);

        User user = authService.findUserByToken(accessToken,TokenSubject.ACCESS_TOKEN);
        checkUserIdForBookMark(userId,user);

        Page<PostBookMark> searchResult = postBookMarkService.getBookMarks(userId,
                PageRequest.of(
                        currentPageAndPageSize.getCurrentPage() - 1,
                        currentPageAndPageSize.getPageSize(),
                        Sort.by("createdDate").descending()
                )
        );

        return null;
    }

    @ApiOperation("게시글 북마크 추가")
    @PostMapping("/{userId}/bookmark/posts)")
    @ResponseStatus(HttpStatus.CREATED)
    public GetPostResponse addBookmarkPost(@RequestHeader String accessToken, @PathVariable Long userId, @PathVariable Long postId) {
        User user = authService.findUserByToken(accessToken, TokenSubject.ACCESS_TOKEN);
        checkUserIdForBookMark(userId, user);
        Post post = postBookMarkService.insertBookMarkInPost(user, postId);
        return new GetPostResponse(detailedPostOf(post,userId));
    }

    @ApiOperation("게시글 북마크 취소")
    @DeleteMapping("/{userId}/bookmark/posts/{postId)")
    @ResponseStatus(HttpStatus.OK)
    public DeleteBookmarkPostResponse deleteBookmarkPost(@PathVariable Long userId, @PathVariable Long postId) {
        /**
         * author : 최민성
         * 현재 엔드포인트만 잡은 상태
         */
        return null;
    }

    public void checkUserIdForBookMark(Long userId, User user) {
        if (!userId.equals(user.getId())) {
            throw new NoPermissionBookMarkException();
        }
    }

    public DetailedPost detailedPostOf(Post post, Long userId){
        DetailedPost detailedPost =new DetailedPost(post);
        detailedPost.setLikes(postService.countPostLike(post.getId()));
        detailedPost.setBookMarks(postBookMarkService.countPostBookMark(post.getId()));
        detailedPost.setLikes(commentService.findCommentCount(post.getId()));
        detailedPost.setIsPressLike(postService.checkUserPressLike(userId, post.getId()));

        return detailedPost;
    }

    private List<DetailedPost> detailedPostsOf(List<PostBookMark> postBookMarks, Long userId) {

        return null;
    }
}
