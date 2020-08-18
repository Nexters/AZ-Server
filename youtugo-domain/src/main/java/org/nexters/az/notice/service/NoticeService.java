package org.nexters.az.notice.service;

import lombok.RequiredArgsConstructor;
import org.nexters.az.comment.exception.NoPermissionAccessCommentException;
import org.nexters.az.notice.entity.Notice;
import org.nexters.az.notice.exception.NoPermissionAccessNoticeException;
import org.nexters.az.notice.exception.NotExistentNoticeException;
import org.nexters.az.notice.repository.NoticeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public void insertNotice(Notice notice){
        noticeRepository.save(notice);
    }

    public Page<Notice> getNotices(Long userId, Pageable pageable){
        return noticeRepository.findAllByUserId(userId, pageable);
    }

    public void deleteNotice(Long userId, Long noticeId){
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(NotExistentNoticeException::new);
        checkWriter(userId, notice.getUser().getId());
        noticeRepository.delete(notice);
    }

    private void checkWriter(Long accessId, Long writerId) {
        if (accessId.equals(writerId))
            return;
        throw new NoPermissionAccessNoticeException();
    }
}
