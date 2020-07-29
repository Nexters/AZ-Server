package org.nexters.az.common.validation;

import org.nexters.az.common.exception.PageSizeRangeException;
import org.nexters.az.sample.exception.TestNoFoundException;
import org.nexters.az.validator.Validator;

public class PageSizeValidation extends Validator<Integer> {
    public PageSizeValidation() {
        super(new PageSizeRangeException());
    }

    @Override
    public boolean test(Integer page) {
        return page >= 1;
    }
}
