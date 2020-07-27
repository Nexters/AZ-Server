package org.nexters.az.user.dto;

import lombok.*;
import org.nexters.az.user.entity.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class SimpleUser {
    private long id;
    private String identification;
    private String nickname;

    public static SimpleUser of(User user) {
        return SimpleUser.builder()
                .id(user.getId())
                .identification(user.getIdentification())
                .nickname(user.getNickname())
                .build();
    }
}
