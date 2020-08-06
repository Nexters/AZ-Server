package org.nexters.az.post.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.nexters.az.auth.security.TokenSubject;
import org.nexters.az.auth.service.AuthService;
import org.nexters.az.comment.service.CommentService;
import org.nexters.az.common.dto.CurrentPageAndPageSize;
import org.nexters.az.common.dto.SimplePage;
import org.nexters.az.common.validation.PageValidation;
import org.nexters.az.post.dto.DetailedPost;
import org.nexters.az.post.entity.Post;
import org.nexters.az.post.exception.NoPermissionDeletePostException;
import org.nexters.az.post.request.WritePostRequest;
import org.nexters.az.post.response.*;
import org.nexters.az.post.service.PostService;
import org.nexters.az.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/posts")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;
    private final AuthService authService;

    @ApiOperation("게시글 작성")
    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public WritePostResponse writePost(
        @RequestHeader String accessToken,
        @RequestBody WritePostRequest writePostRequest
    ) {
        User user = authService.findUserByToken(accessToken, TokenSubject.ACCESS_TOKEN);

        Post post = Post.builder()
                .author(user)
                .content(writePostRequest.getContent())
                .build();
        post = postService.create(post);

        return new WritePostResponse(detailedPostOf(post, user.getId()));
    }

    @ApiOperation("게시글들 조회")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public GetPostsResponse getPosts(
        @RequestHeader(required = false, defaultValue = "") String accessToken,
        @RequestParam(required = false, defaultValue = "1") int currentPage,
        @RequestParam(required = false, defaultValue = "10") int size
    ) {
        Long userId = null;
        if(!accessToken.equals("")) {
            userId = authService.findUserIdBy(accessToken, TokenSubject.ACCESS_TOKEN);
        }

        CurrentPageAndPageSize currentPageAndPageSize = PageValidation.getInstance().verify(currentPage, size);

        Page<Post> searchResult = postService.getPosts(
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
        List<DetailedPost> detailedPosts = detailedPostsOf(searchResult.getContent(), userId);

        return new GetPostsResponse(detailedPosts, simplePage);
    }

    @ApiOperation("인기 게시글 조회")
    @GetMapping("/popular")
    @ResponseStatus(HttpStatus.OK)
    public GetPostsResponse getPopularPosts(
        @RequestHeader(required = false, defaultValue = "") String accessToken,
        @RequestParam(required = false, defaultValue = "1") int currentPage,
        @RequestParam(required = false, defaultValue = "10") int size
    ) {
        Long userId = null;
        if(!accessToken.equals("")) {
            userId = authService.findUserIdBy(accessToken, TokenSubject.ACCESS_TOKEN);
        }

        /**
         * @author : 최민성
         * 현재 엔드포인트만 잡은 상태
         */
        return null;
    }

    @ApiOperation("게시글 상세보기")
    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public GetPostResponse getPost(
        @RequestHeader(required = false, defaultValue = "") String accessToken,
        @PathVariable Long postId
    ) {
        Long userId = null;
        if(!accessToken.equals("")) {
            userId = authService.findUserIdBy(accessToken, TokenSubject.ACCESS_TOKEN);
        }

        DetailedPost detailedPost = detailedPostOf(postService.getPost(postId), userId);

        return new GetPostResponse(detailedPost);
    }

    @ApiOperation("게시글 삭제")
    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
    public void deletePost(
        @RequestHeader String accessToken,
        @PathVariable Long postId
    ) {
        Long userId = authService.findUserIdBy(accessToken, TokenSubject.ACCESS_TOKEN);

        Post post = postService.getPost(postId);
        if(!post.getAuthor().getId().equals(userId)) {
            throw new NoPermissionDeletePostException();
        }

        postService.deletePost(postId, userId);
    }

    @ApiOperation("게시글 추천")
    @PostMapping("/{postId}/likes")
    @ResponseStatus(HttpStatus.CREATED)
    public GetPostResponse insertLikeInPost(
        @RequestHeader String accessToken,
        @PathVariable Long postId
    ) {
        User user = authService.findUserByToken(accessToken, TokenSubject.ACCESS_TOKEN);

        Post post = postService.insertLikeInPost(user, postId);

        return new GetPostResponse(detailedPostOf(post, user.getId()));
   }

    private List<DetailedPost> detailedPostsOf(List<Post> posts, Long userId) {
        List<DetailedPost> detailedPosts = new ArrayList<>();
        posts.forEach(post -> detailedPosts.add(detailedPostOf(post, userId)));

        return detailedPosts;
    }

    private DetailedPost detailedPostOf(Post post, Long userId) {
        DetailedPost detailedPost = new DetailedPost(post);
        detailedPost.setLikes(findPostLikeCount(post.getId()));
        detailedPost.setComments(findPostCommentCount(post.getId()));
        detailedPost.setBookMarks(findPostBookmarkCount(post.getId()));
        if (userId != null)
            detailedPost.setPressLike(checkUserPressLike(userId, post.getId()));

        return detailedPost;
    }

    private int findPostLikeCount(Long postId) {
        return postService.countPostLike(postId);
    }

    private int findPostCommentCount(Long postId) {
        return commentService.findCommentCount(postId);
    }

    private int findPostBookmarkCount(Long postId) {
        /**
         * @author : 최민성
         * @Param : 게시글 id
         * @retrun : bookmark 개수
         */
        return 0;
    }

    private boolean checkUserPressLike(Long userId, Long postId) {
        return postService.checkUserPressLike(userId, postId);
    }

}
