package org.nexters.az.user.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.nexters.az.auth.security.TokenSubject;
import org.nexters.az.auth.service.AuthService;
import org.nexters.az.comment.dto.DetailedComment;
import org.nexters.az.comment.entity.Comment;
import org.nexters.az.comment.response.GetCommentsResponse;
import org.nexters.az.comment.service.CommentService;
import org.nexters.az.common.dto.CurrentPageAndPageSize;
import org.nexters.az.common.dto.SimplePage;
import org.nexters.az.common.validation.PageValidation;
import org.nexters.az.exception.UnauthorizedException;
import org.nexters.az.notice.dto.DetailedNotice;
import org.nexters.az.notice.entity.Notice;
import org.nexters.az.notice.entity.NoticeType;
import org.nexters.az.user.exception.NoPermissionNoticeException;
import org.nexters.az.user.response.GetNoticesResponse;
import org.nexters.az.notice.service.NoticeService;
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

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/users")
public class UserController {
    private final UserService userService;
    private final AuthService authService;
    private final PostService postService;
    private final PostBookMarkService postBookMarkService;
    private final CommentService commentService;
    private final NoticeService noticeService;

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
    public GetPostsResponse getUserPosts(@RequestHeader String accessToken, @PathVariable Long userId,
                                         @RequestParam(required = false, defaultValue = "1") int currentPage,
                                         @RequestParam(required = false, defaultValue = "10") int size) {

        Long accessTokenId = authService.findUserIdBy(accessToken, TokenSubject.ACCESS_TOKEN);
        checkUserIdForBookMark(userId, accessTokenId);

        CurrentPageAndPageSize currentPageAndPageSize = PageValidation.getInstance().verify(currentPage, size);

        Page<Post> resultPostsPages = postService.getPostsByAuthor(userId,
                PageRequest.of(
                        currentPageAndPageSize.getCurrentPage()-1,
                        currentPageAndPageSize.getPageSize(),
                        Sort.by("createdDate").descending()
                ));

        SimplePage simplePage = SimplePage.builder()
                .currentPage(resultPostsPages.getNumber())
                .totalPages(resultPostsPages.getTotalPages())
                .totalElements(resultPostsPages.getTotalElements())
                .build();

        return new GetPostsResponse(postService.detailedPostsOf(resultPostsPages.getContent(),userId),simplePage);
    }

    @ApiOperation("내가 작성한 댓글 조회")
    @GetMapping("/{userId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public GetCommentsResponse getUserComments(@RequestHeader String accessToken, @PathVariable Long userId,
                                               @RequestParam(required = false, defaultValue = "1") int currentPage,
                                               @RequestParam(required = false, defaultValue = "10") int size) {

        Long accessTokenId = authService.findUserIdBy(accessToken, TokenSubject.ACCESS_TOKEN);
        checkUserIdForBookMark(userId, accessTokenId);

        CurrentPageAndPageSize currentPageAndPageSize = PageValidation.getInstance().verify(currentPage, size);

        Page<Comment> resultCommentsPages = commentService.getCommentsByWriter(userId,
                PageRequest.of(currentPageAndPageSize.getCurrentPage()-1,
                        currentPageAndPageSize.getPageSize(),
                        Sort.by("CreatedDate").descending()));

        SimplePage simplePage = SimplePage.builder()
                .currentPage(resultCommentsPages.getNumber())
                .totalPages(resultCommentsPages.getTotalPages())
                .totalElements(resultCommentsPages.getTotalElements())
                .build();

        return new GetCommentsResponse(DetailedComment.detailedCommentsOf(resultCommentsPages.getContent()),simplePage);
    }

    @ApiOperation("알림 리스트 조회")
    @GetMapping("/{userId}/notices")
    @ResponseStatus(HttpStatus.OK)
    public GetNoticesResponse getNotices(@RequestHeader String accessToken,
                                         @PathVariable Long userId,
                                         @RequestParam(required = false, defaultValue = "1") int currentPage,
                                         @RequestParam(required = false, defaultValue = "10") int size) {

        User user = authService.findUserByToken(accessToken, TokenSubject.ACCESS_TOKEN);
        checkUserIdForNotice(userId,user.getId());

        CurrentPageAndPageSize currentPageAndPageSize = PageValidation.getInstance().verify(currentPage, size);

        Page<Notice> notices = noticeService.getNotices(user.getId(),
                PageRequest.of(
                        currentPageAndPageSize.getCurrentPage() - 1,
                        currentPageAndPageSize.getPageSize(),
                        Sort.by("createdDate").descending()
                )
        );

        SimplePage simplePage = SimplePage.builder()
                .currentPage(notices.getNumber())
                .totalPages(notices.getTotalPages())
                .totalElements(notices.getTotalElements())
                .build();

        return new GetNoticesResponse(detailedNoticesOf(notices.getContent()),simplePage);
    }

    @ApiOperation("알림 삭제")
    @DeleteMapping("{userId}/notices/{noticeId}")
    @ResponseStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
    public void deleteNotice(@RequestHeader String accessToken,
                             @PathVariable Long userId,
                             @PathVariable Long noticeId) {

        User user = authService.findUserByToken(accessToken,TokenSubject.ACCESS_TOKEN);
        checkUserIdForNotice(userId,user.getId());
        noticeService.deleteNotice(userId, noticeId);
    }

    public void checkUserIdForBookMark(Long userId, Long accessTokenUserId) {
        if (!userId.equals(accessTokenUserId)) {
            throw new NoPermissionBookMarkException();
        }
    }

    public void checkUserIdForNotice(Long userId, Long accessTokenUserId) {
        if (!userId.equals(accessTokenUserId)) {
            throw new NoPermissionNoticeException();
        }
    }

    private List<DetailedPost> detailedPostsOf(Stream<PostBookMark> postBookMarks, Long userId) {
        List<DetailedPost> detailedPosts = new ArrayList<>();
        postBookMarks.forEach(post -> detailedPosts.add(postService.detailedPostOf(post.getPost(), userId)));
        detailedPosts.forEach(DetailedPost::makeSimpleContent);

        return detailedPosts;
    }

    public  List<DetailedNotice> detailedNoticesOf(List<Notice> notices) {
        List<DetailedNotice> detailedNotices = new ArrayList<>();
        notices.forEach(notice -> detailedNotices.add(detailedNoticeOf(notice)));

        return detailedNotices;
    }

    public  DetailedNotice detailedNoticeOf(Notice notice){

        DetailedPost detailedPost = postService.detailedPostOf(notice.getPost(),notice.getPost().getAuthor().getId());
        String noticeMessage = "";

        switch (notice.getNoticeType()){
            case COMMENT:
                noticeMessage = notice.getResponder().getNickname() + NoticeType.COMMENT.getMessage();
                break;
            case LIKE:
                noticeMessage = notice.getResponder().getNickname() + NoticeType.LIKE.getMessage();
                break;
        }

        return new DetailedNotice(detailedPost,notice,noticeMessage);
    }

}
