package org.nexters.az.post.apis;

import org.junit.Test;
import org.nexters.az.common.CommonTest;
import org.nexters.az.post.response.GetPostResponse;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

public class DeletePostApiTest extends CommonTest {

    @Test
    public void testDeletePostAndCheckSuccess() throws Exception {
        //given
        String accessToken = createUser().getAccessToken().getToken();
        GetPostResponse getPostResponse = createPost(accessToken);
        Long postId = getPostResponse.getDetailedPost().getId();

        //when
        MvcResult mvcResult = mockMvc.perform(
                delete(POST_URL + "/" + postId)
                    .header("accessToken", accessToken)
                    .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        //then
        assertEquals(203, mvcResult.getResponse().getStatus());
    }

    @Test
    public void testDeletePostAndCheckFailBecauseNoPermissionDeletePostException() throws Exception {
        //given
        String accessToken = createUser().getAccessToken().getToken();
        GetPostResponse getPostResponse = createPost(accessToken);
        Long postId = getPostResponse.getDetailedPost().getId();

        String anotherAccessToken = createUser().getAccessToken().getToken();

        //when
        MvcResult mvcResult = mockMvc.perform(
                delete(POST_URL + "/" + postId)
                        .header("accessToken", anotherAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        //then
        assertEquals(403, mvcResult.getResponse().getStatus());
    }

}
