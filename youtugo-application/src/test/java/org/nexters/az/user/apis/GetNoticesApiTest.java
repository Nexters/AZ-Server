package org.nexters.az.user.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.nexters.az.auth.response.SignInResponse;
import org.nexters.az.common.CommonTest;
import org.nexters.az.post.response.GetPostResponse;
import org.nexters.az.user.entity.Rating;
import org.nexters.az.user.response.GetRatingResponse;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class GetNoticesApiTest extends CommonTest {

    @Test
    public void testGetNoticesSuccess() throws Exception {

        //given
        SignInResponse signInResponse = createUser();
        String accessToken = signInResponse.getAccessToken().getToken();
        Long userId = signInResponse.getUser().getId();


        //when
        MvcResult mvcResult = mockMvc.perform(
                get(USER_URL+"/"+userId+"/notices")
                        .header("accessToken",accessToken)
        ).andReturn();

        //then
        assertEquals(200, mvcResult.getResponse().getStatus());

    }

    @Test
    public void testGetNoticesFailBecauseNoPermissionNoticeException() throws Exception {
        //given
        SignInResponse signInResponse = createUser();
        Long userId = signInResponse.getUser().getId();

        //when
        MvcResult mvcResult = mockMvc.perform(
                get(USER_URL+"/"+userId+"/notices")
                        .header("accessToken", "wrongAccessToken")
        ).andReturn();

        //then
        assertEquals(403, mvcResult.getResponse().getStatus());
    }
}
