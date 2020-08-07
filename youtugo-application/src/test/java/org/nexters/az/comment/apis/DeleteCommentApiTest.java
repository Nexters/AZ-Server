package org.nexters.az.comment.apis;

import org.junit.Test;
import org.nexters.az.comment.response.WriteCommentResponse;
import org.nexters.az.common.CommonTest;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

public class DeleteCommentApiTest extends CommonTest {

    @Test
    public void testDeleteCommentSuccess() throws  Exception {

        //given
        String accessToken = createUser().getAccessToken().getToken();
        String writerAccessToken = createUser().getAccessToken().getToken();
        Long postId = createPost(writerAccessToken).getDetailedPost().getId();
        WriteCommentResponse writeCommentResponse = createComment(postId,accessToken);
        Long commentId = writeCommentResponse.getDetailedComment().getId();

        //when
        MvcResult mvcResult = mockMvc.perform(
                delete(POST_URL+"/"+postId+"/comments/"+commentId)
                .header("accessToken",accessToken)
        ).andReturn();

        //then
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    public void testDeleteCommentFailBecauseNoPermissionAccessCommentException() throws Exception {

        //given
        String accessToken = createUser().getAccessToken().getToken();
        String writerAccessToken = createUser().getAccessToken().getToken();
        Long postId = createPost(writerAccessToken).getDetailedPost().getId();
        WriteCommentResponse writeCommentResponse = createComment(postId,accessToken);
        Long commentId = writeCommentResponse.getDetailedComment().getId();

        //when
        MvcResult mvcResult = mockMvc.perform(
                delete(POST_URL+"/"+postId+"/comments/"+commentId)
                        .header("accessToken","wrongAccessToken")
        ).andReturn();

        //then
        assertEquals(403,mvcResult.getResponse().getStatus());
    }

    @Test
    public void testDeleteCommentFailBecauseNonExistentCommentException() throws Exception {

        //given
        String accessToken = createUser().getAccessToken().getToken();
        String writerAccessToken = createUser().getAccessToken().getToken();
        Long postId = createPost(writerAccessToken).getDetailedPost().getId();
        WriteCommentResponse writeCommentResponse = createComment(postId,accessToken);
        Long wrongCommentId = 10000L;

        //when
        MvcResult mvcResult = mockMvc.perform(
                delete(POST_URL+"/"+postId+"/comments/"+wrongCommentId)
                        .header("accessToken",accessToken)
        ).andReturn();

        //then
        assertEquals(404,mvcResult.getResponse().getStatus());
    }
}
