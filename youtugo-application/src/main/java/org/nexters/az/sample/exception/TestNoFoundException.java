package org.nexters.az.sample.exception;

import org.nexters.az.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.NotFound;

public class TestNoFoundException extends NotFoundException {
    public TestNoFoundException() {
        super("테스트 익셉션입니다.");
    }
}
