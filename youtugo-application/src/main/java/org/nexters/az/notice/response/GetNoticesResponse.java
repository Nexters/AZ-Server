package org.nexters.az.notice.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.nexters.az.common.dto.SimplePage;
import org.nexters.az.notice.dto.DetailedNotice;
import org.nexters.az.post.dto.DetailedPost;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetNoticesResponse {
    private List<DetailedNotice> detailedNoticeList;
    private SimplePage simplePage;

    public GetNoticesResponse(List<DetailedNotice> detailedNoticeList, SimplePage simplePage) {
        this.detailedNoticeList = detailedNoticeList;
        this.simplePage = simplePage;
    }
}
