package org.nexters.az.user.apis;

import org.junit.Test;
import org.nexters.az.auth.response.SignInResponse;
import org.nexters.az.comment.response.WriteCommentResponse;
import org.nexters.az.common.CommonTest;
import org.nexters.az.notice.dto.DetailedNotice;
import org.nexters.az.notice.entity.Notice;
import org.nexters.az.notice.repository.NoticeRepository;
import org.nexters.az.post.response.GetPostResponse;
import org.nexters.az.user.response.GetNoticesResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

public class DeleteNoticeApiTest extends CommonTest {

    @Test
    public void testDeleteNoticeSuccess() throws Exception {
        //given
        SignInResponse responser = createUser();
        String responserAccessToken = responser.getAccessToken().getToken();

        SignInResponse writer = createUser();
        String  writerAccessToken= writer.getAccessToken().getToken();
        Long writerId = writer.getUser().getId();
        GetPostResponse getPostResponse =createPost(writerAccessToken);
        WriteCommentResponse writeCommentResponse =createComment(getPostResponse.getDetailedPost().getId(),responserAccessToken);

        GetNoticesResponse getNoticesResponse = createNotice(writerId,writerAccessToken);
        Long noticeId = getNoticesResponse.getDetailedNoticeList().get(0).getNoticeId();

        //when
        MvcResult mvcResult = mockMvc.perform(
                delete(USER_URL+"/"+writerId+"/notices/"+noticeId)
                        .header("accessToken",writerAccessToken)
        ).andReturn();

        //then
        assertEquals(203, mvcResult.getResponse().getStatus());
    }

    @Test
    public void testDeleteNoticeFailBecauseNotExistentNoticeException() throws Exception {
        //given
        SignInResponse signInResponse = createUser();
        String accessToken = signInResponse.getAccessToken().getToken();
        Long userId = signInResponse.getUser().getId();

        //when
        MvcResult mvcResult = mockMvc.perform(
                delete(USER_URL+"/"+userId+"/notices/"+1)
                        .header("accessToken",accessToken)
        ).andReturn();

        //then
        assertEquals(404, mvcResult.getResponse().getStatus());
    }
}
