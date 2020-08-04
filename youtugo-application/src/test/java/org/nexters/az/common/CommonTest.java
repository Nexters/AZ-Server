package org.nexters.az.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.nexters.az.auth.dto.AccessToken;
import org.nexters.az.auth.request.SignUpRequest;
import org.nexters.az.auth.response.SignInResponse;
import org.nexters.az.user.dto.SimpleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class CommonTest {
    @LocalServerPort
    protected int port;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;

    protected final String URL = "http://localhost:";

    private static int testNO = 0;

    protected static final String COMMON_PW = "1234567890123456789012345678901234567890123456789012345678901234";

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    protected SignInResponse createUser() throws Exception{
        SignUpRequest signUpRequest = createSignUpRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        MvcResult mvcResult = mockMvc.perform(
                post(URL + port + "/v1/api/auth" + "/sign-up")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(signUpRequest))
        ).andReturn();

        return objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                SignInResponse.class
        );
    }

    protected SignUpRequest createSignUpRequest() {
        testNO++;

        return new SignUpRequest(
                "test" + testNO,
                "test" + testNO,
                COMMON_PW
        );
    }
}
