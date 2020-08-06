package org.nexters.az.user.apis;

import org.junit.Test;
import org.nexters.az.common.CommonTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class CheckIdentificationExistApiTest extends CommonTest {

    @Test
    public void testCheckNicknameExistAndCheckSuccess() throws Exception {
        //given
        String identification = "testCheckNicknameExistAndCheckSuccess";

        //when
        MvcResult mvcResult = mockMvc.perform(
                post(USER_URL + "/identifications/" + identification + "/existence")
                    .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        //then
        assertEquals(204, mvcResult.getResponse().getStatus());
    }

    @Test
    public void testCheckNicknameExistAndCheckFailWhenAlreadyNicknameExistException() throws Exception {
        //given
        String identification = createUser().getUser().getIdentification();

        //when
        MvcResult mvcResult = mockMvc.perform(
                post(USER_URL + "/identifications/" + identification + "/existence")
                    .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        //then
        assertEquals(409, mvcResult.getResponse().getStatus());
    }
}
