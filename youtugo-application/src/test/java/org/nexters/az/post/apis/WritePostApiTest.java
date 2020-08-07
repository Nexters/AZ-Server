package org.nexters.az.post.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.nexters.az.common.CommonTest;
import org.nexters.az.post.request.WritePostRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class WritePostApiTest extends CommonTest {

    @Test
    public void testWritePostAndCheckSuccess() throws Exception {
        //given
        String accessToken = createUser().getAccessToken().getToken();
        WritePostRequest writePostRequest = new WritePostRequest("test_success");
        String request = new ObjectMapper().writeValueAsString(writePostRequest);

        //when
        MvcResult mvcResult = mockMvc.perform(
                post(POST_URL + "/post")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("accessToken", accessToken)
                    .content(request)
        ).andReturn();

        //then
        assertEquals(201, mvcResult.getResponse().getStatus());
    }

    @Test
    public void testWritePostAndCheckFailBecauseTokenInvalid() throws Exception {
        //given
        WritePostRequest writePostRequest = new WritePostRequest("test_success");
        String request = new ObjectMapper().writeValueAsString(writePostRequest);

        //when
        MvcResult mvcResult = mockMvc.perform(
            post(POST_URL + "/post")
                .contentType(MediaType.APPLICATION_JSON)
                .header("accessToken", "test_fail")
                .content(request)
        ).andReturn();

        //then
        assertEquals(403, mvcResult.getResponse().getStatus());
    }

}
