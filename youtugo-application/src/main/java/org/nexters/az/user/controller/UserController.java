package org.nexters.az.user.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.nexters.az.auth.security.TokenSubject;
import org.nexters.az.auth.service.AuthService;
import org.nexters.az.comment.response.GetCommentsResponse;
import org.nexters.az.common.dto.CurrentPageAndPageSize;
import org.nexters.az.common.dto.SimplePage;
import org.nexters.az.common.validation.PageValidation;
import org.nexters.az.exception.UnauthorizedException;
import org.nexters.az.post.dto.DetailedPost;
import org.nexters.az.post.entity.Post;
import org.nexters.az.post.entity.PostBookMark;
import org.nexters.az.post.response.GetPostResponse;
import org.nexters.az.post.response.GetPostsResponse;
import org.nexters.az.post.service.PostBookMarkService;
import org.nexters.az.post.service.PostService;
import org.nexters.az.user.entity.User;
import org.nexters.az.user.exception.NoPermissionBookMarkException;
import org.nexters.az.user.response.GetRatingResponse;
import org.nexters.az.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/users")
public class UserController {
    private final UserService userService;
    private final AuthService authService;
    private final PostService postService;
    private final PostBookMarkService postBookMarkService;

    @ApiOperation("닉네임 중복 체크")
    @PostMapping("/nicknames/{nickname}/existence")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void checkNicknameExist(@PathVariable String nickname) {
        userService.checkUserNicknameExist(nickname);
    }

    @ApiOperation("아이디 중복 체크")
    @PostMapping("/identifications/{identification}/existence")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void checkIdentificationExist(@PathVariable String identification) {
        userService.checkUserIdentificationExist(identification);
    }

    @ApiOperation("등급 조건 조회")
    @GetMapping("/{userId}/rating")
    @ResponseStatus(HttpStatus.OK)
    public GetRatingResponse getRating(@RequestHeader String accessToken, @PathVariable Long userId) {
        if (!userId.equals(authService.findUserIdBy(accessToken, TokenSubject.ACCESS_TOKEN))) {
            throw new UnauthorizedException("User cannot access");
        }

        return new GetRatingResponse(userService.updateRating(userId));
    }

    @ApiOperation("내가 북마크한 글 조회")
    @GetMapping("/{userId}/bookmark/posts")
    @ResponseStatus(HttpStatus.OK)
    public GetPostsResponse getBookmarkPosts(@RequestHeader String accessToken, @PathVariable Long userId,
                                             @RequestParam(required = false, defaultValue = "1") int currentPage,
                                             @RequestParam(required = false, defaultValue = "10") int size) {

        CurrentPageAndPageSize currentPageAndPageSize = PageValidation.getInstance().verify(currentPage, size);

        User user = authService.findUserByToken(accessToken, TokenSubject.ACCESS_TOKEN);
        checkUserIdForBookMark(userId, user.getId());

        Page<PostBookMark> searchBookMarks = postBookMarkService.getBookMarks(userId,
                PageRequest.of(
                        currentPageAndPageSize.getCurrentPage() - 1,
                        currentPageAndPageSize.getPageSize(),
                        Sort.by("id").descending()
                )
        );

        SimplePage simplePage = SimplePage.builder()
                .currentPage(searchBookMarks.getNumber())
                .totalPages(searchBookMarks.getTotalPages())
                .totalElements(searchBookMarks.getTotalElements())
                .build();

        return new GetPostsResponse(detailedPostsOf(searchBookMarks.stream(),userId),simplePage);
    }

    @ApiOperation("게시글 북마크 추가")
    @PostMapping("/bookmark/posts/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    public GetPostResponse addBookmarkPost(@RequestHeader String accessToken, @PathVariable Long postId) {

        User user = authService.findUserByToken(accessToken, TokenSubject.ACCESS_TOKEN);

        Post post = postBookMarkService.insertBookMarkInPost(user, postId);

        return new GetPostResponse(postService.detailedPostOf(post, user.getId()));
    }

    @ApiOperation("게시글 북마크 취소")
    @DeleteMapping("/bookmark/posts/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBookmarkPost(@RequestHeader String accessToken, @PathVariable Long postId) {

        User user = authService.findUserByToken(accessToken, TokenSubject.ACCESS_TOKEN);

        Post post = postService.getPost(postId);

        postBookMarkService.deleteBookMark(user,post);
    }

    @ApiOperation("내가 작성한 글 조회")
    @GetMapping("/{userId}/posts")
    @ResponseStatus(HttpStatus.OK)
    public GetPostsResponse getUserPosts(@PathVariable Long userId) {
        /**
         * author : 최민성
         * 현재 엔드포인트만 잡은 상태
         */
        return null;
    }

    @ApiOperation("내가 작성한 댓글 조회")
    @GetMapping("/{userId}/comments)")
    @ResponseStatus(HttpStatus.OK)
    public GetCommentsResponse getUserComments(@PathVariable Long userId) {
        /**
         * author : 최민성
         * 현재 엔드포인트만 잡은 상태
         */
        return null;
    }

    public void checkUserIdForBookMark(Long userId, Long accessTokenUserId) {
        if (!userId.equals(accessTokenUserId)) {
            throw new NoPermissionBookMarkException();
        }
    }

    private List<DetailedPost> detailedPostsOf(Stream<PostBookMark> postBookMarks, Long userId) {
        List<DetailedPost> detailedPosts = new ArrayList<>();
        postBookMarks.forEach(post -> detailedPosts.add(postService.detailedPostOf(post.getPost(), userId)));
        detailedPosts.forEach(DetailedPost::makeSimpleContent);

        return detailedPosts;
    }

}
