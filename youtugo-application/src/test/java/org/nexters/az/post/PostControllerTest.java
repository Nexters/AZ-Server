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

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    // TODO 1 : 게시글 작성 성공

    // TODO 2 : 게시글 작성 실패 - 권한 없음

    // TODO 3 : 게시글 상세보기 성공

    // TODO 4 : 게시글 상세보기 실패 - 존재하지 않는 게시글

    // TODO 5 : 게시글 삭제 성공

    // TODO 6 : 게시글 삭제 실패 - 삭제 권한 없음

    // TODO 7 : 게시글 삭제 실패 - 존재하지 않는 게시글

}
