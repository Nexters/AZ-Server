package org.nexters.az.user.apis;

import org.junit.Test;
import org.nexters.az.common.CommonTest;
import org.nexters.az.post.response.GetPostResponse;
import org.springframework.test.web.servlet.MvcResult;
import static org.junit.Assert.assertEquals;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

public class DeleteBookMarkPostApiTest extends CommonTest {

    @Test
    public void deleteBookMarkPostSuccess() throws Exception {

        //given
        String accessToken = createUser().getAccessToken().getToken();
        String writerAccessToken = createUser().getAccessToken().getToken();
        Long postId = createPost(writerAccessToken).getDetailedPost().getId();
        addBookMark(accessToken,postId);

        //when
        MvcResult mvcResult = mockMvc.perform(
                delete(USER_URL+"/bookmark/posts/"+ postId)
                .header("accessToken",accessToken)
        ).andReturn();

        //then
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    public void deleteBookMarkPostFailBecauseNonExistentBookMarkException() throws Exception {

        //given
        String accessToken = createUser().getAccessToken().getToken();
        String writerAccessToken = createUser().getAccessToken().getToken();
        Long postId = createPost(writerAccessToken).getDetailedPost().getId();
        addBookMark(accessToken,postId);
        Long wrongPostId = 7L;

        //when
        MvcResult mvcResult = mockMvc.perform(
                delete(USER_URL+"/bookmark/posts/"+ wrongPostId)
                .header("accessToken",accessToken)
        ).andReturn();

        //then
        assertEquals(404,mvcResult.getResponse().getStatus());
    }

}
