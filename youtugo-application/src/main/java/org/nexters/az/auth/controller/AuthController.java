package org.nexters.az.auth.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.nexters.az.auth.dto.AccessToken;
import org.nexters.az.auth.request.SignUpRequest;
import org.nexters.az.auth.response.RefreshAccessTokenResponse;
import org.nexters.az.auth.response.SignInResponse;
import org.nexters.az.auth.service.AuthService;
import org.nexters.az.user.dto.SimpleUser;
import org.nexters.az.user.entity.User;
import org.nexters.az.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/auth")
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    @ApiOperation("회원가입")
    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public SignInResponse signUp(@RequestBody SignUpRequest signUpRequest) {
        User user = User.builder()
                .identification(signUpRequest.getIdentification())
                .nickname(signUpRequest.getNickname())
                .hashedPassword(signUpRequest.getPassword())
                .build();

        user = userService.signUp(user);

       return makeSignInResponse(user);
    }

    @ApiOperation("로그인")
    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public SignInResponse signIn(@RequestBody SignUpRequest signUpRequest) {
        User user = userService.signIn(signUpRequest.getIdentification(), signUpRequest.getPassword());

        return makeSignInResponse(user);
    }

    @ApiOperation("엑세스 토큰 재발급")
    @GetMapping("/access-token")
    @ResponseStatus(HttpStatus.CREATED)
    public RefreshAccessTokenResponse refreshAccessToken(@RequestHeader String refreshToken) {
        AccessToken accessToken = authService.generateAccessTokenBy(refreshToken);

        return new RefreshAccessTokenResponse(accessToken);
    }

    private SignInResponse makeSignInResponse(User user) {
        AccessToken accessToken = authService.generateAccessTokenBy(user);
        String refreshToken = authService.generateRefreshToken(user);

        return new SignInResponse(SimpleUser.of(user), accessToken, refreshToken);
    }
}
