package org.nexters.az.common.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SimplePage {
    private int currentPage;
    private int totalPages;
    private long totalElements;

    @Builder
    public SimplePage(int currentPage, int totalPages, long totalElements) {
        this.currentPage = currentPage + 1;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }
}
