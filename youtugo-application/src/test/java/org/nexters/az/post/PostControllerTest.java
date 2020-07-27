package org.nexters.az.post;

import org.junit.Test;
import org.nexters.az.common.CommonTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;


import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class PostControllerTest extends CommonTest {
    private final String POST_URL = URL + port + "/v1/api/posts";

    @Test
    public void testSearchPostsSuccess() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                get(POST_URL)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
        assertEquals(200, mvcResult.getResponse().getStatus());
    }
}
