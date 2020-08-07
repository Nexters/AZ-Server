package org.nexters.az.comment.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.nexters.az.comment.request.ModifyCommentRequest;
import org.nexters.az.comment.response.WriteCommentResponse;
import org.nexters.az.common.CommonTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

public class ModifyCommentApiTest extends CommonTest {

    @Test
    public void testModifyCommentSuccess() throws Exception {

        //given
        String accessToken = createUser().getAccessToken().getToken();
        String writerAccessToken = createUser().getAccessToken().getToken();
        Long postId = createPost(writerAccessToken).getDetailedPost().getId();
        WriteCommentResponse writeCommentResponse = createComment(postId,accessToken);
        Long commentId = writeCommentResponse.getDetailedComment().getId();
        ModifyCommentRequest modifyCommentRequest = new ModifyCommentRequest("modifyCommentTest");
        String request = new ObjectMapper().writeValueAsString(modifyCommentRequest);

        //when
        MvcResult mvcResult = mockMvc.perform(
                patch(POST_URL+"/"+postId+"/comments/"+commentId)
                .header("accessToken",accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andReturn();

        //then
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    public void testModifyCommentFailBecauseNonExistentCommentException() throws Exception {

        //given
        String accessToken = createUser().getAccessToken().getToken();
        String writerAccessToken = createUser().getAccessToken().getToken();
        Long postId = createPost(writerAccessToken).getDetailedPost().getId();
        Long wrongCommentId = 100000L;
        ModifyCommentRequest modifyCommentRequest = new ModifyCommentRequest("modifyCommentTest");
        String request = new ObjectMapper().writeValueAsString(modifyCommentRequest);

        //when
        MvcResult mvcResult = mockMvc.perform(
                patch(POST_URL+"/"+postId+"/comments/"+wrongCommentId)
                        .header("accessToken",accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
        ).andReturn();

        //then
        assertEquals(404,mvcResult.getResponse().getStatus());
    }

    @Test
    public void testModifyCommentFailBecauseNoPermissionAccessCommentException() throws Exception {

        //given
        String accessToken = createUser().getAccessToken().getToken();
        String writerAccessToken = createUser().getAccessToken().getToken();
        Long postId = createPost(writerAccessToken).getDetailedPost().getId();
        WriteCommentResponse writeCommentResponse = createComment(postId,accessToken);
        Long commentId = writeCommentResponse.getDetailedComment().getId();
        ModifyCommentRequest modifyCommentRequest = new ModifyCommentRequest("modifyCommentTest");
        String request = new ObjectMapper().writeValueAsString(modifyCommentRequest);

        //when
        MvcResult mvcResult = mockMvc.perform(
                patch(POST_URL+"/"+postId+"/comments/"+commentId)
                        .header("accessToken","wrongAccessToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
        ).andReturn();

        //then
        assertEquals(403,mvcResult.getResponse().getStatus());
    }
}
