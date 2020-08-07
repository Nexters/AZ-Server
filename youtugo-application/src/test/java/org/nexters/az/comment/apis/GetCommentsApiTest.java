package org.nexters.az.comment.apis;

import org.junit.Test;
import org.nexters.az.common.CommonTest;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class GetCommentsApiTest extends CommonTest {

    @Test
    public void testGetCommentsSuccess() throws Exception {

        //given
        String writerAccessToken = createUser().getAccessToken().getToken();
        Long postId = createPost(writerAccessToken).getDetailedPost().getId();

        //when
        MvcResult mvcResult = mockMvc.perform(
                get(POST_URL + "/" + postId + "/comments")
        ).andReturn();

        //then
        assertEquals(200, mvcResult.getResponse().getStatus());
    }
}
