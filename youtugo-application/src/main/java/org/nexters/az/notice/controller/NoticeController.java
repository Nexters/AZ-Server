package org.nexters.az.notice.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.nexters.az.auth.security.TokenSubject;
import org.nexters.az.auth.service.AuthService;
import org.nexters.az.common.dto.CurrentPageAndPageSize;
import org.nexters.az.common.dto.SimplePage;
import org.nexters.az.common.validation.PageValidation;
import org.nexters.az.notice.dto.DetailedNotice;
import org.nexters.az.notice.entity.Notice;
import org.nexters.az.notice.entity.NoticeType;
import org.nexters.az.notice.response.GetNoticesResponse;
import org.nexters.az.notice.service.NoticeService;
import org.nexters.az.post.dto.DetailedPost;
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
@RequestMapping("v1/api/notice")
public class NoticeController {
    private final NoticeService noticeService;
    private final AuthService authService;
    private final PostService postService;

    @ApiOperation("알림 리스트 조회")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public GetNoticesResponse getNotices(@RequestHeader String accessToken,
                                         @RequestParam(required = false, defaultValue = "1") int currentPage,
                                         @RequestParam(required = false, defaultValue = "10") int size) {

        User user = authService.findUserByToken(accessToken, TokenSubject.ACCESS_TOKEN);

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
    @DeleteMapping("/{noticeId}")
    @ResponseStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
    public void deleteNotice(@RequestHeader String accessToken,
                             @PathVariable Long noticeId) {
        Long userId = authService.findUserIdBy(accessToken, TokenSubject.ACCESS_TOKEN);

        noticeService.deleteNotice(noticeId);
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
                noticeMessage = notice.getNickName() + NoticeType.COMMENT.getMessage();
                break;
            case LIKE:
                noticeMessage = notice.getNickName() + NoticeType.LIKE.getMessage();
                break;
        }

        return new DetailedNotice(detailedPost,notice.getId(),notice.getNoticeType(),noticeMessage);
    }
}
