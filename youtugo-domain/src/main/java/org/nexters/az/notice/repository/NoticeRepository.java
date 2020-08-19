package org.nexters.az.notice.repository;

import org.nexters.az.common.repository.ExtendRepository;
import org.nexters.az.notice.entity.Notice;
import org.nexters.az.notice.entity.NoticeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface NoticeRepository extends ExtendRepository<Notice> {

    Page<Notice> findAllByUserId(Long userId, Pageable pageable);

}
