package org.nexters.az.user.apis;

import org.junit.Test;
import org.nexters.az.common.CommonTest;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class AddBookMarkPostApiTest extends CommonTest {

    @Test
    public void testAddBookMarkInPostSuccess() throws Exception {

        //given
        String accessToken = createUser().getAccessToken().getToken();
        String writerAccessToken = createUser().getAccessToken().getToken();
        Long postId = createPost(writerAccessToken).getDetailedPost().getId();

        //when
        MvcResult mvcResult = mockMvc.perform(
                post(USER_URL + "/bookmark/" + postId)
                        .header("accessToken", accessToken)
        ).andReturn();

        //then
        assertEquals(201, mvcResult.getResponse().getStatus());
    }

    @Test
    public void testAddBookMarkInPostFailBecauseTokenInvalid() throws Exception {

        //given
        String writerAccessToken = createUser().getAccessToken().getToken();
        Long postId = createPost(writerAccessToken).getDetailedPost().getId();

        //when
        MvcResult mvcResult = mockMvc.perform(
                post(USER_URL + "/bookmark/" + postId)
                        .header("accessToken", "tokenNotFound")
        ).andReturn();

        //then
        assertEquals(403, mvcResult.getResponse().getStatus());
    }
}
