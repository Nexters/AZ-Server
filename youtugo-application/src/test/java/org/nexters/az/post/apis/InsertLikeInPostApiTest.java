package org.nexters.az.post.apis;

import org.junit.Test;
import org.nexters.az.common.CommonTest;
import org.nexters.az.post.response.GetPostResponse;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class InsertLikeInPostApiTest extends CommonTest {

    @Test
    public void testInsertLikeInPostAndCheckSuccess() throws Exception {
        //given
        String accessToken = createUser().getAccessToken().getToken();
        GetPostResponse getPostResponse = createPost(accessToken);
        Long postId = getPostResponse.getDetailedPost().getId();

        //when
        MvcResult mvcResult = mockMvc.perform(
                post(POST_URL + "/" + postId + "/likes")
                        .header("accessToken", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        //then
        assertEquals(201, mvcResult.getResponse().getStatus());
    }

    @Test
    public void testInsertLikeInPostAndCheckFailBecauseUserAlreadyPressLikeException() throws Exception {
        //given
        String accessToken = createUser().getAccessToken().getToken();
        GetPostResponse getPostResponse = createPost(accessToken);
        Long postId = getPostResponse.getDetailedPost().getId();
        mockMvc.perform(
                post(POST_URL + "/" + postId + "/likes")
                        .header("accessToken", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        //when
        MvcResult mvcResult = mockMvc.perform(
                post(POST_URL + "/" + postId + "/likes")
                        .header("accessToken", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        //then
        assertEquals(409, mvcResult.getResponse().getStatus());
    }

}
