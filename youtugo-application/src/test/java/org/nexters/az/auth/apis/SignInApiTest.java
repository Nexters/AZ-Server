package org.nexters.az.auth.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.nexters.az.auth.request.SignInRequest;
import org.nexters.az.common.CommonTest;
import org.nexters.az.user.dto.SimpleUser;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class SignInApiTest extends CommonTest {

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
        assertEquals(401, mvcResult.getResponse().getStatus());
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
}
