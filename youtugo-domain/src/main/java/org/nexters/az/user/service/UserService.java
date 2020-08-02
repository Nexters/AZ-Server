package org.nexters.az.user.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.nexters.az.comment.repository.CommentRepository;
import org.nexters.az.post.repository.PostRepository;
import org.nexters.az.user.dto.RatingForPromotion;
import org.nexters.az.user.entity.Rating;
import org.nexters.az.user.entity.User;
import org.nexters.az.user.exception.NonExistentUserException;
import org.nexters.az.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public RatingForPromotion updateRating(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NonExistentUserException::new);
        int postCount = postRepository.countAllByAuthorId(userId);
        int commentCount = commentRepository.countAllByWriterId(userId);

        RatingForPromotion ratingForPromotion = findRatingForPromotion(postCount, commentCount);
        user.setRating(ratingForPromotion.getCurrentRating());

        return ratingForPromotion;
    }

    public RatingForPromotion findRatingForPromotion(int postCount, int commentCount) {
        Rating currentRating = null;
        Rating nextRating = null;
        int postCountForPromotion = 0;
        int commentCountForPromotion = 0;

        if (postCount < 5 || commentCount < 3) {
            currentRating = Rating.NEW_RECRUIT;
            nextRating = Rating.ASSISTANT_MANAGE;
            postCountForPromotion = 5 - postCount;
            commentCountForPromotion = 3 - commentCount;
        }
        else if (postCount < 10 || commentCount < 15) {
            currentRating = Rating.ASSISTANT_MANAGE;
            nextRating = Rating.DEPARTMENT_HEAD;
            postCountForPromotion = 10 - postCount;
            commentCountForPromotion = 15 - commentCount;
        }
        else if (postCount < 50 || commentCount < 50) {
            currentRating = Rating.DEPARTMENT_HEAD;
            nextRating = Rating.MANAGING_DIRECTOR;
            postCountForPromotion = 50 - postCount;
            commentCountForPromotion = 50 - commentCount;
        }
        else if (postCount < 100 || commentCount < 100) {
            currentRating = Rating.MANAGING_DIRECTOR;
            nextRating = Rating.BOSS;
            postCountForPromotion = 100 - postCount;
            commentCountForPromotion = 100 - commentCount;
        }
        else {
            currentRating = Rating.BOSS;
        }

        postCountForPromotion = Math.max(postCountForPromotion, 0);
        commentCountForPromotion = Math.max(commentCountForPromotion, 0);


        return RatingForPromotion.builder()
                .currentRating(currentRating)
                .nextRating(nextRating)
                .commentCountForPromotion(commentCountForPromotion)
                .postCountForPromotion(postCountForPromotion)
                .build();
    }
}
