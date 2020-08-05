package org.nexters.az.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.nexters.az.auth.request.SignInRequest;
import org.nexters.az.auth.request.SignUpRequest;
import org.nexters.az.auth.response.SignInResponse;
import org.nexters.az.common.CommonTest;
import org.nexters.az.user.dto.SimpleUser;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class AuthControllerTest extends CommonTest {
    private final String AUTH_URL = URL + port + "/v1/api/auth";

    @Test
    public void signUpAndCheckSuccess() throws Exception {
        //given
        SignUpRequest signUpRequest = createSignUpRequest();

        ObjectMapper request = new ObjectMapper();

        //when
        MvcResult mvcResult = mockMvc.perform(
                post(AUTH_URL + "/sign-up")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(request.writeValueAsString(signUpRequest))
        ).andReturn();

        //then
        assertEquals(201, mvcResult.getResponse().getStatus());
    }

    @Test
    public void signUpAndCheckFailBecauseDuplicatedIdentification() throws Exception {
        //given
        SignInResponse existUser = createUser();
        SignUpRequest signUpRequest = new SignUpRequest(
            existUser.getUser().getIdentification(),
            "test_fail",
            COMMON_PW
        );
        ObjectMapper request = new ObjectMapper();

        //when
        MvcResult mvcResult = mockMvc.perform(
                post(AUTH_URL + "/sign-up")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(request.writeValueAsString(signUpRequest))
        ).andReturn();

        //then
        assertEquals(403, mvcResult.getResponse().getStatus());
    }

    @Test
    public void signUpAndCheckFailBecauseDuplicatedNickname() throws Exception {
        //given
        SignInResponse signUpUser = createUser();
        SignUpRequest signUpRequest = new SignUpRequest(
                "test_fail",
                signUpUser.getUser().getNickname(),
                COMMON_PW
        );
        ObjectMapper request = new ObjectMapper();

        //when
        MvcResult mvcResult = mockMvc.perform(
                post(AUTH_URL + "/sign-up")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(request.writeValueAsString(signUpRequest))
        ).andReturn();

        //then
        assertEquals(403, mvcResult.getResponse().getStatus());
    }

    @Test
    public void signInAndCheckSuccess() throws Exception {
        //given
        SimpleUser user = createUser().getUser();
        SignInRequest signInRequest = new SignInRequest(user.getIdentification(), COMMON_PW);
        ObjectMapper request = new ObjectMapper();

        //when
        MvcResult mvcResult = mockMvc.perform(
                post(AUTH_URL + "/sign-in")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(request.writeValueAsString(signInRequest))
        ).andReturn();

        //then
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void signInAndCheckFailBecauseNonExistUser() throws Exception {
        //given
        SignInRequest signInRequest = new SignInRequest("test_fail", COMMON_PW);
        ObjectMapper request = new ObjectMapper();

        //when
        MvcResult mvcResult = mockMvc.perform(
                post(AUTH_URL + "/sign-in")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(request.writeValueAsString(signInRequest))
        ).andReturn();

        //then
        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    public void signInAndCheckFailBecausePasswordMismatch() throws Exception {
        //given
        SimpleUser user = createUser().getUser();
        SignInRequest signInRequest = new SignInRequest(user.getIdentification(), "test_fail");
        ObjectMapper request = new ObjectMapper();

        //when
        MvcResult mvcResult = mockMvc.perform(
                post(AUTH_URL + "/sign-in")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(request.writeValueAsString(signInRequest))
        ).andReturn();

        //then
        assertEquals(401, mvcResult.getResponse().getStatus());
    }

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
