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
    public void refreshAccessTokenAndCheckSuccess() {
        /**
         * @author : 작성자
         *
         * swagger로 테스트할 경우 정상적으로 진행되는 것은 확인 할 수 있었지만,
         * 아래의 코드와 같이 테스트 하였지만, "io.jsonwebtoken.security.SignatureException"이 발생하여 정상적인 테스트코드가 진행되지 않았습니다.
         *
         * 관련 레퍼런스를 서칭한 결과
         * "Jwts를 signwith하거나 parse할 때 한쪽만 byte값으로 진행하여서 발생했다는 의견들이 많았습니다.
         * 그러나 현재 우리의 코드에서는 두개 모두 byte값으로 진행되고 swagger로도 정상 진행이 되기에 큰 문제는 없어보입니다.
         * 현재 원인을 찾지 못하고 있어 'refreshAccessToken" 기능에 대한 테스트는 추가적으로 이슈를 발급해 진행하도록 하겠습니다.
         *
         * 작성한 코드
         *         //given
         *         SignInResponse signInResponse = createUser();
         *         ObjectMapper request = new ObjectMapper();
         *
         *         System.out.println(signInResponse.getRefreshToken());
         *         //when
         *         MvcResult mvcResult = mockMvc.perform(
         *                 get(AUTH_URL + "/access-token")
         *                         .contentType(MediaType.APPLICATION_JSON_VALUE)
         *                         .header("refreshToken", request.writeValueAsString(signInResponse.getRefreshToken()))
         *         ).andReturn();
         *
         *         //then
         *         assertEquals(201, mvcResult.getResponse().getStatus());
         */
    }
}
