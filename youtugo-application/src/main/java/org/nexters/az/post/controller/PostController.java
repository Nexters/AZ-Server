package org.nexters.az.post.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.nexters.az.common.dto.SimplePage;
import org.nexters.az.post.dto.DetailedPost;
import org.nexters.az.post.entity.Post;
import org.nexters.az.post.request.WritePostRequest;
import org.nexters.az.post.response.SearchPostsResponse;
import org.nexters.az.post.response.WritePostResponse;
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

    @ApiOperation("게시글 작성")
    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public WritePostResponse writePost(
        @RequestHeader String accessToken,
        @RequestBody WritePostRequest writePostRequest
    ) {
        System.out.println(writePostRequest);
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
    public SearchPostsResponse searchPosts(
        @RequestParam(required = false, defaultValue = "0") int currentPage,
        @RequestParam(required = false, defaultValue = "10") int size
    ) {
        Page<Post> searchResult = postService.search(PageRequest.of(currentPage, size, Sort.by("createdDate").descending()));
        SimplePage simplePage = SimplePage.builder()
                .currentPage(searchResult.getNumber())
                .totalPages(searchResult.getTotalPages())
                .totalElements(searchResult.getTotalElements())
                .build();
        List<DetailedPost> detailedPosts = detailedPostsOf(searchResult.getContent());

        return new SearchPostsResponse(detailedPosts, simplePage);
    }

    private List<DetailedPost> detailedPostsOf(List<Post> posts) {
        List<DetailedPost> detailedPosts = new ArrayList<>();
        posts.forEach(post -> {
            detailedPosts.add(detailedPostOf(post));
        });

        return detailedPosts;
    }

    private DetailedPost detailedPostOf(Post post) {
        // TODO 1: PostLikeService를 통해 좋아요 수 찾기(아래 코드는 가데이터)
        int likes = 3;
        // TODO 2: PostBookmarService를 통해 북마크 수 찾기(아래 코드는 가데이터)
        int bookMarks = 10;

        return new DetailedPost(post, likes, bookMarks);
    }
}
