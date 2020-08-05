package org.nexters.az.auth.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.nexters.az.auth.dto.AccessToken;
import org.nexters.az.user.dto.SimpleUser;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponse {
    private SimpleUser user;
    private AccessToken accessToken;
    private String refreshToken;
}
