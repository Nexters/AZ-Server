package org.nexters.az.comment.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.nexters.az.comment.request.WriteCommentRequest;
import org.nexters.az.common.CommonTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class WriteCommentApiTest extends CommonTest {

    @Test
    public void testWriteCommentSuccess() throws Exception {

        //given
        String accessToken = createUser().getAccessToken().getToken();
        String writerAccessToken = createUser().getAccessToken().getToken();
        Long postId = createPost(writerAccessToken).getDetailedPost().getId();
        WriteCommentRequest writeCommentRequest = new WriteCommentRequest("testComment");
        String request = new ObjectMapper().writeValueAsString(writeCommentRequest);

        //when
        MvcResult mvcResult = mockMvc.perform(
                post(POST_URL + "/" + postId + "/comments/comment")
                        .header("accessToken", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
        ).andReturn();

        //then
        assertEquals(201, mvcResult.getResponse().getStatus());
    }

    @Test
    public void testWriteCommentFailBecauseTokenInvalid() throws Exception {

        //given
        String writeAccessToken = createUser().getAccessToken().getToken();
        Long postId = createPost(writeAccessToken).getDetailedPost().getId();
        WriteCommentRequest writeCommentRequest = new WriteCommentRequest("testComment");
        String request = new ObjectMapper().writeValueAsString(writeCommentRequest);

        //when
        MvcResult mvcResult = mockMvc.perform(
                post(POST_URL + "/" + postId + "/comments/comment")
                        .header("accessToken", "wrongToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
        ).andReturn();

        //then
        assertEquals(403, mvcResult.getResponse().getStatus());
    }
}
