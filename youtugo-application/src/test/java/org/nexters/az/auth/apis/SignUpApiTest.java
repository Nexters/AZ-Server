package org.nexters.az.auth.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.nexters.az.auth.request.SignUpRequest;
import org.nexters.az.auth.response.SignInResponse;
import org.nexters.az.common.CommonTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class SignUpApiTest extends CommonTest {

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

}
