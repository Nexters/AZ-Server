package org.nexters.az.common.validation;

import org.nexters.az.common.exception.PageSizeRangeException;
import org.nexters.az.validator.Validator;

public class PageSizeValidation extends Validator<Integer> {
    public PageSizeValidation() {
        super(new PageSizeRangeException());
    }

    @Override
    public boolean test(Integer pageSize) {
        return pageSize >= 1;
    }
}
