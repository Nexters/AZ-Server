package org.nexters.az.post.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.nexters.az.post.dto.DetailedPost;
import org.nexters.az.post.entity.Post;
import org.nexters.az.post.request.WritePostRequest;
import org.nexters.az.post.response.WritePostResponse;
import org.nexters.az.post.service.PostService;
import org.nexters.az.user.entity.User;
import org.nexters.az.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/posts")
public class PostController {
    private final PostService postService;
    // TODO 4: 추후 [TODO 1] 이 구현됨에 따라 삭제될 예정
    private final UserRepository userRepository;

    @ApiOperation("도서관 생성")
    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public WritePostResponse write(WritePostRequest writePostRequest) {
        // TODO 1: 추후 accessToken으로 사용자를 찾기(아래 코드는 가데이터)
        User user = User.builder()
                .identification("test")
                .nickname("test")
                .hashedPassword("test")
                .build();
        user = userRepository.save(user);
        // TODO 2: PostLikeService를 통해 좋아요 수 찾기(아래 코드는 가데이터)
        int likes = 3;
        // TODO 3: PostBookmarService를 통해 북마크 수 찾기(아래 코드는 가데이터)
        int bookMarks = 10;

        Post post = Post.builder()
                        .author(user)
                        .content(writePostRequest.getContent())
                        .build();
        post = postService.create(post);
        DetailedPost detailedPost = new DetailedPost(post, likes, bookMarks);

        return new WritePostResponse(detailedPost);
    }
}
