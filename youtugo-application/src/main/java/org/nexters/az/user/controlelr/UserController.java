package org.nexters.az.user.controlelr;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.nexters.az.auth.security.TokenSubject;
import org.nexters.az.auth.service.AuthService;
import org.nexters.az.comment.response.GetCommentsResponse;
import org.nexters.az.exception.UnauthorizedException;
import org.nexters.az.post.response.GetPostResponse;
import org.nexters.az.post.response.GetPostsResponse;
import org.nexters.az.user.response.DeleteBookmarkPostResponse;
import org.nexters.az.user.response.GetRatingResponse;
import org.nexters.az.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/users")
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    @ApiOperation("닉네임 중복 체크")
    @PostMapping("/nicknames/{nickname}/existence")
    @ResponseStatus(HttpStatus.OK)
    public void checkNicknameExist(@PathVariable String nickname) {
        userService.checkUserNicknameExist(nickname);
    }

    @ApiOperation("아이디 중복 체크")
    @PostMapping("/identifications/{identification}/existence")
    @ResponseStatus(HttpStatus.OK)
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

    @ApiOperation("내가 작성한 글 조회")
    @GetMapping("/{userId}/posts)")
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

    @ApiOperation("게시글 북미크 조회")
    @GetMapping("/{userId}/bookmark/posts)")
    @ResponseStatus(HttpStatus.OK)
    public GetPostsResponse getBookmarkPosts(@PathVariable Long userId) {
        /**
         * author : 최민성
         * 현재 엔드포인트만 잡은 상태
         */
        return null;
    }

    @ApiOperation("게시글 북마크 추가")
    @PostMapping("/{userId}/bookmark/posts)")
    @ResponseStatus(HttpStatus.CREATED)
    public GetPostResponse addBookmarkPost(@PathVariable Long userId, @PathVariable Long postId) {
        /**
         * author : 최민성
         * 현재 엔드포인트만 잡은 상태
         */
        return null;
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
}
