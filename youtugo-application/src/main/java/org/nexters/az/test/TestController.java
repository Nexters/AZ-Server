package org.nexters.az.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/testing")
    public String test() {
        return "This is test for git action";
    }
}
