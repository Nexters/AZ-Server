package org.nexters.az.validator;

import org.springframework.web.server.ResponseStatusException;

import java.util.function.Predicate;

public abstract class Validator<T> implements Predicate<T> {
    private ResponseStatusException responseStatusException;

    public Validator(ResponseStatusException responseStatusException) {
        this.responseStatusException = responseStatusException;
    }

    public void verify(T t) {
        if (!test(t))
            throw responseStatusException;
    }
}
