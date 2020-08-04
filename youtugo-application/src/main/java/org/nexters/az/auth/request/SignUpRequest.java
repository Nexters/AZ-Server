package org.nexters.az.auth.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpRequest {
    private String identification;
    private String nickname;
    private String password;
}
