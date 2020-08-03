package org.nexters.az.common.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CurrentPageAndPageSize {
    private int currentPage;
    private int pageSize;

    @Builder
    public CurrentPageAndPageSize(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }
}
