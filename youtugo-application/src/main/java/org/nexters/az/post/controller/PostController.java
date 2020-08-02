package org.nexters.az.post.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.nexters.az.common.exception.CurrentPageRangeException;
import org.nexters.az.common.exception.PageSizeRangeException;
import org.nexters.az.common.validation.CurrentPageValidation;
import org.nexters.az.common.validation.PageSizeValidation;
import org.nexters.az.common.dto.SimplePage;
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

    // TODO 1 : 삭제예정
    private final UserRepository userRepository;

    private final PageSizeValidation pageSizeValidation = new PageSizeValidation();
    private final CurrentPageValidation currentPageValidation = new CurrentPageValidation();

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

        return new WritePostResponse(detailedPostOf(post));
    }

    @ApiOperation("게시글들 조회")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public GetPostsResponse getPosts(
        @RequestParam(required = false, defaultValue = "1") int currentPage,
        @RequestParam(required = false, defaultValue = "10") int size
    ) {
        try {
            currentPageValidation.verify(currentPage);
        } catch (CurrentPageRangeException currentPageRangeException) {
            currentPage = 1;
        }
        try {
            pageSizeValidation.verify(size);
        } catch (PageSizeRangeException pageSizeRangeException) {
            size = 10;
        }

        Page<Post> searchResult = postService.getPosts(PageRequest.of(currentPage - 1, size, Sort.by("createdDate").descending()));
        SimplePage simplePage = SimplePage.builder()
                .currentPage(searchResult.getNumber())
                .totalPages(searchResult.getTotalPages())
                .totalElements(searchResult.getTotalElements())
                .build();
        List<DetailedPost> detailedPosts = detailedPostsOf(searchResult.getContent());

        return new GetPostsResponse(detailedPosts, simplePage);
    }

    @ApiOperation("인기 게시글 조회")
    @GetMapping("/popular")
    @ResponseStatus(HttpStatus.OK)
    public GetPostsResponse getPopularPosts(
        @RequestParam(required = false, defaultValue = "1") int currentPage,
        @RequestParam(required = false, defaultValue = "10") int size
    ) {
        /**
         * 현재 엔드포인트만 잡은 상태
         */
        return null;
    }

    @ApiOperation("게시글 상세보기")
    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public GetPostResponse getPost(@PathVariable Long postId) {
        DetailedPost detailedPost =  detailedPostOf(postService.getPost(postId));

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
         * 현재 엔드포인트만 잡은 상태
         */
        return null;
    }

    private List<DetailedPost> detailedPostsOf(List<Post> posts) {
        List<DetailedPost> detailedPosts = new ArrayList<>();
        posts.forEach(post -> detailedPosts.add(detailedPostOf(post)));

        return detailedPosts;
    }

    private DetailedPost detailedPostOf(Post post) {
        DetailedPost detailedPost = new DetailedPost(post);
        // TODO 1: PostLikeService를 통해 좋아요 수 찾기(아래 코드는 가데이터)
        detailedPost.setLikes(10);
        // TODO 2: PostBookmarService를 통해 북마크 수 찾기(아래 코드는 가데이터)
        detailedPost.setBookMarks(3);
        // TODO 3 : 자신의 추천 정보
        detailedPost.setIsPressLike(false);

        return detailedPost;
    }
}
