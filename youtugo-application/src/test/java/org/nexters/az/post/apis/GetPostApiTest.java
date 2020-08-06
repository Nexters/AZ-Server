package org.nexters.az.post.apis;

import org.junit.Test;
import org.nexters.az.common.CommonTest;
import org.nexters.az.post.response.GetPostResponse;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class GetPostApiTest extends CommonTest {

    @Test
    public void testGetPostAndCheckSuccessWhenGuest() throws Exception {
        //given
        String accessToken = createUser().getAccessToken().getToken();
        GetPostResponse getPostResponse = createPost(accessToken);
        Long postId = getPostResponse.getDetailedPost().getId();

        //when
        MvcResult mvcResult = mockMvc.perform(
                get(POST_URL + "/" + postId)
                    .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        //then
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void testGetPostAndCheckSuccessWhenUser() throws Exception {
        //given
        String accessToken = createUser().getAccessToken().getToken();
        GetPostResponse getPostResponse = createPost(accessToken);
        Long postId = getPostResponse.getDetailedPost().getId();

        //when
        MvcResult mvcResult = mockMvc.perform(
                get(POST_URL + "/" + postId)
                    .header("accessToken", accessToken)
                    .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        //then
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

}
