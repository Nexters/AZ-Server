package org.nexters.az.post.apis;

import org.junit.Test;
import org.nexters.az.common.CommonTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class GetPostsApiTest extends CommonTest {

    @Test
    public void testGetPostsSuccess() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                get(POST_URL)
                    .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

}
