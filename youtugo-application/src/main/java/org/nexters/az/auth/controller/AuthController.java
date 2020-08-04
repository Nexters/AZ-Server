package org.nexters.az.auth.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.nexters.az.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/auth")
public class AuthController {
    private final UserService userService;

    @ApiOperation("회원가입")
    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp() {
        // TODO : 아이디 중복검사
        // TODO : 닉네임 중복검사
    }

    @ApiOperation("로그인")
    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public void signIn() {

    }

    @ApiOperation("엑세스 토큰 재발급")
    @GetMapping("/access-token")
    @ResponseStatus(HttpStatus.OK)
    public void refreshAccessToken() {

    }
}
