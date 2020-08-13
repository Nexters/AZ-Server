package org.nexters.az.user.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.nexters.az.auth.response.SignInResponse;
import org.nexters.az.common.CommonTest;
import org.nexters.az.post.response.GetPostResponse;
import org.nexters.az.user.entity.Rating;
import org.nexters.az.user.response.GetRatingResponse;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class GetRatingApiTest extends CommonTest {

    @Test
    public void getRatingWhenUserIsNewRecruit() throws Exception {

        //given
        SignInResponse signInResponse = createUser();
        String accessToken = signInResponse.getAccessToken().getToken();
        long userId = signInResponse.getUser().getId();
        Rating testRating = Rating.NEW_RECRUIT;

        //when
        MvcResult mvcResult = mockMvc.perform(
                get(USER_URL+"/"+userId+"/rating")
                        .header("accessToken",accessToken)
        ).andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        GetRatingResponse getRatingResponse = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                GetRatingResponse.class
        );

        //then
        assertEquals(testRating, getRatingResponse.getRatingForPromotion().getCurrentRating());
    }

    @Test
    public void getRatingWhenUserIsAssistantManage() throws Exception {
        //given
        SignInResponse signInResponse = createUser();
        String accessToken = signInResponse.getAccessToken().getToken();
        long userId = signInResponse.getUser().getId();
        Rating testRating = Rating.ASSISTANT_MANAGE;


        GetPostResponse getPostsResponse = createPost(accessToken);
        for(int i = 0; i < testRating.getPostCount() - 1; i++) {
            createPost(accessToken);
        }
        for (int i = 0; i < testRating.getCommentCount(); i++) {
            createComment(getPostsResponse.getDetailedPost().getId(), accessToken);
        }

        //when
        MvcResult mvcResult = mockMvc.perform(
                get(USER_URL+"/"+userId+"/rating")
                        .header("accessToken",accessToken)
        ).andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        GetRatingResponse getRatingResponse = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                GetRatingResponse.class
        );

        //then
        assertEquals(testRating, getRatingResponse.getRatingForPromotion().getCurrentRating());
    }

    @Test
    public void getRatingWhenUserIsDepartmentHead() throws Exception {
        //given
        SignInResponse signInResponse = createUser();
        String accessToken = signInResponse.getAccessToken().getToken();
        long userId = signInResponse.getUser().getId();
        Rating testRating = Rating.DEPARTMENT_HEAD;

        GetPostResponse getPostsResponse = createPost(accessToken);
        for(int i = 0; i < testRating.getPostCount() - 1; i++) {
            createPost(accessToken);
        }
        for (int i = 0; i < testRating.getCommentCount(); i++) {
            createComment(getPostsResponse.getDetailedPost().getId(), accessToken);
        }

        //when
        MvcResult mvcResult = mockMvc.perform(
                get(USER_URL+"/"+userId+"/rating")
                        .header("accessToken",accessToken)
        ).andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        GetRatingResponse getRatingResponse = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                GetRatingResponse.class
        );

        //then
        assertEquals(testRating, getRatingResponse.getRatingForPromotion().getCurrentRating());
    }

    @Test
    public void getRatingWhenUserIsManagingDirector() throws Exception {
        //given
        SignInResponse signInResponse = createUser();
        String accessToken = signInResponse.getAccessToken().getToken();
        long userId = signInResponse.getUser().getId();
        Rating testRating = Rating.MANAGING_DIRECTOR;


        GetPostResponse getPostsResponse = createPost(accessToken);
        for(int i = 0; i < testRating.getPostCount() - 1; i++) {
            createPost(accessToken);
        }
        for (int i = 0; i < testRating.getCommentCount(); i++) {
            createComment(getPostsResponse.getDetailedPost().getId(), accessToken);
        }

        //when
        MvcResult mvcResult = mockMvc.perform(
                get(USER_URL+"/"+userId+"/rating")
                        .header("accessToken",accessToken)
        ).andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        GetRatingResponse getRatingResponse = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                GetRatingResponse.class
        );

        //then
        assertEquals(testRating, getRatingResponse.getRatingForPromotion().getCurrentRating());
    }

    @Test
    public void getRatingWhenUserIsBoss() throws Exception {
        //given
        SignInResponse signInResponse = createUser();
        String accessToken = signInResponse.getAccessToken().getToken();
        long userId = signInResponse.getUser().getId();
        Rating testRating = Rating.DEPARTMENT_HEAD;


        GetPostResponse getPostsResponse = createPost(accessToken);
        for(int i = 0; i < testRating.getPostCount() - 1; i++) {
            createPost(accessToken);
        }
        for (int i = 0; i < testRating.getCommentCount(); i++) {
            createComment(getPostsResponse.getDetailedPost().getId(), accessToken);
        }

        //when
        MvcResult mvcResult = mockMvc.perform(
                get(USER_URL+"/"+userId+"/rating")
                        .header("accessToken",accessToken)
        ).andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        GetRatingResponse getRatingResponse = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                GetRatingResponse.class
        );

        //then
        assertEquals(testRating, getRatingResponse.getRatingForPromotion().getCurrentRating());
    }

}
