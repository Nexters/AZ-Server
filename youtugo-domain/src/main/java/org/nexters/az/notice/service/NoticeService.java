package org.nexters.az.notice.service;

import lombok.RequiredArgsConstructor;
import org.nexters.az.comment.dto.DetailedComment;
import org.nexters.az.comment.entity.Comment;
import org.nexters.az.notice.dto.DetailedNotice;
import org.nexters.az.notice.entity.Notice;
import org.nexters.az.notice.entity.NoticeType;
import org.nexters.az.notice.exception.NotExistentNoticeException;
import org.nexters.az.notice.repository.NoticeRepository;
import org.nexters.az.post.entity.Post;
import org.nexters.az.post.service.PostService;
import org.nexters.az.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public void insertNotice(User user, Post post, NoticeType noticeType, String nickName){
        Notice notice = Notice.builder()
                .noticeType(noticeType)
                .post(post)
                .user(user)
                .nickName(nickName).build();
        noticeRepository.save(notice);
    }

    public Page<Notice> getNotices(Long userId, Pageable pageable){
        return noticeRepository.findAllByUserId(userId, pageable);
    }

    public void deleteNotice(Long noticeId){
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(NotExistentNoticeException::new);
        noticeRepository.delete(notice);
    }
}
