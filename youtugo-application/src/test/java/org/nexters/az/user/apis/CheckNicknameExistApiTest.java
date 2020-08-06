package org.nexters.az.user.apis;

import org.junit.Test;
import org.nexters.az.common.CommonTest;
import org.nexters.az.post.response.GetPostResponse;
import org.nexters.az.user.dto.SimpleUser;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class CheckNicknameExistApiTest extends CommonTest {

    @Test
    public void testCheckNicknameExistAndCheckSuccess() throws Exception {
        //given
        String nickname = "test_success";

        //when
        MvcResult mvcResult = mockMvc.perform(
                post(USER_URL + "/nicknames/" + nickname + "/existence")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        //then
        assertEquals(204, mvcResult.getResponse().getStatus());
    }

    @Test
    public void testCheckNicknameExistAndCheckFailWhenAlreadyNicknameExistException() throws Exception {
        //given
        String nickname = createUser().getUser().getNickname();

        //when
        MvcResult mvcResult = mockMvc.perform(
                post(USER_URL + "/nicknames/" + nickname + "/existence")
                    .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        //then
        assertEquals(409, mvcResult.getResponse().getStatus());
    }

}
