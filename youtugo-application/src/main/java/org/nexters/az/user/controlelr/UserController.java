package org.nexters.az.user.controlelr;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.nexters.az.user.response.GetRatingResponse;
import org.nexters.az.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/users")
public class UserController {
    private final UserService userService;

    @ApiOperation("등급 조건 조회")
    @GetMapping("/{userId}/rating")
    public GetRatingResponse getRating(@PathVariable Long userId) {
        //ToDo : 권한 확인
        return new GetRatingResponse(userService.updateRating(userId));
    }

}
