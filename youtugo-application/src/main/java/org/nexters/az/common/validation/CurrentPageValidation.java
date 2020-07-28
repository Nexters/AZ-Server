package org.nexters.az.common.validation;

import org.nexters.az.common.exception.CurrentPageRangeException;
import org.nexters.az.sample.exception.TestNoFoundException;
import org.nexters.az.validator.Validator;

public class CurrentPageValidation extends Validator<Integer> {
    public CurrentPageValidation() {
        super(new CurrentPageRangeException());
    }

    @Override
    public boolean test(Integer currentPage) {
        return currentPage >= 0;
    }
}
