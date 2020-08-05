package org.nexters.az.auth.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.nexters.az.auth.dto.AccessToken;

@Getter
@AllArgsConstructor
public class RefreshAccessTokenResponse {
    private AccessToken accessToken;
}
