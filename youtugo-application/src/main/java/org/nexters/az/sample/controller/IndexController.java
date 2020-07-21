package org.nexters.az.sample.controller;

import io.swagger.annotations.ApiOperation;
import org.nexters.az.sample.exception.TestNoFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
public class IndexController {

  @GetMapping("/")
  public String hello() {
    return "hello world!";
  }

  @GetMapping("/test/{testId}")
  @ApiOperation("테스트")
  @ResponseStatus(HttpStatus.OK)
  public String test(@PathVariable int testId) {
    if(testId >= 0)
      throw new TestNoFoundException();

    return "test";
  }
}
