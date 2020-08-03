package org.nexters.az.post.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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
import org.nexters.az.user.repository.UserRepository;
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

    // TODO 1 : 삭제예정
    private final UserRepository userRepository;

    @ApiOperation("게시글 작성")
    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public WritePostResponse writePost(
        @RequestHeader String accessToken,
        @RequestBody WritePostRequest writePostRequest
    ) {
        // TODO 1: 추후 accessToken으로 사용자를 찾기(아래 코드는 가데이터)
        User user = User.builder()
                .identification("test")
                .nickname("test")
                .hashedPassword("test")
                .build();
        user = userRepository.save(user);

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
        @RequestParam(required = false, defaultValue = "1") int currentPage,
        @RequestParam(required = false, defaultValue = "10") int size
    ) {
        // TODO : 유저 구해야함

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
        List<DetailedPost> detailedPosts = detailedPostsOf(searchResult.getContent(), null);

        return new GetPostsResponse(detailedPosts, simplePage);
    }

    @ApiOperation("인기 게시글 조회")
    @GetMapping("/popular")
    @ResponseStatus(HttpStatus.OK)
    public GetPostsResponse getPopularPosts(
        @RequestParam(required = false, defaultValue = "1") int currentPage,
        @RequestParam(required = false, defaultValue = "10") int size
    ) {
        // TODO : 유저 구해야함
        /**
         * 현재 엔드포인트만 잡은 상태
         */
        return null;
    }

    @ApiOperation("게시글 상세보기")
    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public GetPostResponse getPost(@PathVariable Long postId) {
        // TODO : 유저 구해야함
        DetailedPost detailedPost = detailedPostOf(postService.getPost(postId), null);

        return new GetPostResponse(detailedPost);
    }

    @ApiOperation("게시글 삭제")
    @DeleteMapping("/{postId}")
    public DeletePostResponse deletePost(@PathVariable Long postId) {
        // TODO : 게시글 유저 아이디 확인하기
        Long userId = 10L;

        Post post = postService.getPost(postId);
        if(!post.getAuthor().getId().equals(userId)) {
            throw new NoPermissionDeletePostException();
        }
        postService.deletePost(postId, userId);

        boolean isDeleted = !postService.checkExistPost(postId);


        return new DeletePostResponse(isDeleted);
    }

    @ApiOperation("게시글 추천")
    @PostMapping("/{postId}/likes")
    @ResponseStatus(HttpStatus.CREATED)
    public GetPostResponse insertLikeInPost(@PathVariable Long postId) {
        /**
         * TODO : 유저 확인
         * 1. 유저가 아니라 게스트일 경우
         * - 게스트는 추천을 누를 수 없다는 exception
         *
         * 2. 토큰 값이 있을 경우
         * - 토큰 값을 가지고 유저 정보 찾
         */
        User user = User.builder()
                .identification("test")
                .nickname("test")
                .hashedPassword("test")
                .build();
        user = userRepository.save(user);

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
            detailedPost.setIsPressLike(checkUserPressLike(userId, post.getId()));

        return detailedPost;
    }

    private int findPostLikeCount(Long postId) {
        return postService.countPostLike(postId);
    }

    private int findPostCommentCount(Long postId) {
        return commentService.findCommentCount(postId);
    }

    private int findPostBookmarkCount(Long postId) {
        // TODO : 북마크 갯수
        return 0;
    }

    private boolean checkUserPressLike(Long userId, Long postId) {
        return postService.checkUserPressLike(userId, postId);
    }

}
