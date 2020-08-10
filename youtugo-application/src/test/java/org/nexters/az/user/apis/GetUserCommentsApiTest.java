package org.nexters.az.user.apis;

import org.junit.Test;
import org.nexters.az.auth.response.SignInResponse;
import org.nexters.az.common.CommonTest;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class GetUserCommentsApiTest extends CommonTest {

    @Test
    public void testGetUserCommentsSuccess() throws Exception {

        //given
        SignInResponse signInResponse = createUser();
        Long userId = signInResponse.getUser().getId();
        String accessToken = signInResponse.getAccessToken().getToken();

        //when
        MvcResult mvcResult =mockMvc.perform(
                get(USER_URL+"/"+userId+"/comments")
                .header("accessToken",accessToken)
        ).andReturn();

        //then
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    public void testGetUserCommentsFailBecauseInvalidAccessToken() throws Exception {

        //given
        SignInResponse signInResponse = createUser();
        Long userId = signInResponse.getUser().getId();

        //when
        MvcResult mvcResult =mockMvc.perform(
                get(USER_URL+"/"+userId+"/comments")
                        .header("accessToken","wrongAccessToken")
        ).andReturn();

        //then
        assertEquals(403,mvcResult.getResponse().getStatus());

    }
}
