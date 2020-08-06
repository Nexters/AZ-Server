package org.nexters.az.auth.apis;

import org.junit.Test;
import org.nexters.az.common.CommonTest;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class RefreshAccessTokenApiTest extends CommonTest {

    @Test
    public void refreshAccessTokenAndCheckSuccess() throws Exception {
        //given
        String refreshToken = createUser().getRefreshToken();

        //when
        MvcResult mvcResult = mockMvc.perform(
                get(AUTH_URL + "/access-token")
                        .header("refreshToken", refreshToken)
        ).andReturn();

        //then
        assertEquals(201, mvcResult.getResponse().getStatus());
    }

    @Test
    public void refreshAccessTokenAndCheckFailBecauseInvalidToken() throws Exception {
        //given
        String refreshToken = "token_fail";

        //when
        MvcResult mvcResult = mockMvc.perform(
                get(AUTH_URL + "/access-token")
                        .header("refreshToken", refreshToken)
        ).andReturn();

        //then
        assertEquals(403, mvcResult.getResponse().getStatus());
    }

    @Test
    public void refreshAccessTokenAndCheckFailBecauseMismatchTokenSubject() throws Exception {
        //given
        String refreshToken = createUser().getAccessToken().getToken();

        //when
        MvcResult mvcResult = mockMvc.perform(
                get(AUTH_URL + "/access-token")
                        .header("refreshToken", refreshToken)
        ).andReturn();

        //then
        assertEquals(403, mvcResult.getResponse().getStatus());
    }

}
