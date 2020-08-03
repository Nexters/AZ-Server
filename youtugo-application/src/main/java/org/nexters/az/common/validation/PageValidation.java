package org.nexters.az.common.validation;

import org.nexters.az.common.dto.CurrentPageAndPageSize;
import org.nexters.az.common.exception.CurrentPageRangeException;
import org.nexters.az.common.exception.PageSizeRangeException;

public class PageValidation {
    private CurrentPageValidation currentPageValidation;
    private PageSizeValidation pageSizeValidation;

    private PageValidation() {
        this.currentPageValidation = new CurrentPageValidation();
        this.pageSizeValidation = new PageSizeValidation();
    }

    private static class SingletonHelper {
        private static final PageValidation INSTANCE = new PageValidation();
    }

    public static PageValidation getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public CurrentPageAndPageSize verify(Integer currentPage, Integer pageSize) {
        try {
            currentPageValidation.verify(currentPage);
        } catch (CurrentPageRangeException currentPageRangeException) {
            currentPage = 1;
        }
        try {
            pageSizeValidation.verify(pageSize);
        } catch (PageSizeRangeException pageSizeRangeException) {
            pageSize = 10;
        }

        return CurrentPageAndPageSize.builder()
                .currentPage(currentPage)
                .pageSize(pageSize)
                .build();
    }
}
