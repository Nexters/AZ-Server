package org.nexters.az.user.apis;

import org.junit.Test;
import org.nexters.az.auth.response.SignInResponse;
import org.nexters.az.common.CommonTest;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class GetUserPostsApiTest extends CommonTest {

    @Test
    public void testGetUserPostsSuccess() throws Exception {

        //given
        SignInResponse signInResponse = createUser();
        String accessToken = signInResponse.getAccessToken().getToken();
        Long userId = signInResponse.getUser().getId();

        //when
        MvcResult mvcResult = mockMvc.perform(
                get(USER_URL+"/"+userId+"/posts")
                .header("accessToken",accessToken)
        ).andReturn();

        //then
        assertEquals(200,mvcResult.getResponse().getStatus());

    }

    @Test
    public void testGetUserPostsFailBecauseInvalidAccessToken() throws Exception {

        //given
        SignInResponse signInResponse = createUser();
        Long userId = signInResponse.getUser().getId();

        //when
        MvcResult mvcResult = mockMvc.perform(
                get(USER_URL+"/"+userId+"/posts")
                        .header("accessToken","wrongAccessToken")
        ).andReturn();

        //then
        assertEquals(403,mvcResult.getResponse().getStatus());
    }
}
