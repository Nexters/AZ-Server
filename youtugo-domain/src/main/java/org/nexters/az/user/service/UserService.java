package org.nexters.az.user.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.nexters.az.auth.security.SHA256Util;
import org.nexters.az.comment.repository.CommentRepository;
import org.nexters.az.post.repository.PostRepository;
import org.nexters.az.user.dto.RatingForPromotion;
import org.nexters.az.user.entity.Rating;
import org.nexters.az.user.entity.User;
import org.nexters.az.user.exception.AlreadyIdentificationExistException;
import org.nexters.az.user.exception.AlreadyNicknameExistException;
import org.nexters.az.user.exception.NonExistentUserException;
import org.nexters.az.user.exception.IdentificationOrPasswordMismatchException;
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

    public User signUp(User user) {
        checkUserNicknameExist(user.getIdentification());
        checkUserIdentificationExist(user.getNickname());
        user.setHashedPassword(SHA256Util.of(user.getHashedPassword()));
        return userRepository.save(user);
    }

    public User signIn(String identification, String password) {
        User user = userRepository.findByIdentification(identification).orElseThrow(IdentificationOrPasswordMismatchException::new);
        if (!user.getHashedPassword().equals(SHA256Util.of(password))) {
            throw new IdentificationOrPasswordMismatchException();
        }

        return user;
    }

    public void checkUserNicknameExist(String nickname) {
        if (userRepository.existsAllByNickname(nickname))
            throw new AlreadyNicknameExistException();
    }

    public void checkUserIdentificationExist(String identification) {
        if (userRepository.existsAllByIdentification(identification))
            throw new AlreadyIdentificationExistException();
    }

    public RatingForPromotion updateRating(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NonExistentUserException::new);
        int postCount = postRepository.countAllByAuthorId(userId);
        int commentCount = commentRepository.countAllByWriterId(userId);

        RatingForPromotion ratingForPromotion = findRatingForPromotion(postCount, commentCount);
        user.setRating(ratingForPromotion.getCurrentRating());

        return ratingForPromotion;
    }

    public RatingForPromotion findRatingForPromotion(int postCount, int commentCount) {
        Rating currentRating;
        Rating nextRating;
        int postCountForPromotion = 0;
        int commentCountForPromotion = 0;

        if (postCount < Rating.ASSISTANT_MANAGE.getPostCount() || commentCount < Rating.ASSISTANT_MANAGE.getCommentCount()) {
            currentRating = Rating.NEW_RECRUIT;
            nextRating = Rating.ASSISTANT_MANAGE;
            postCountForPromotion = Rating.ASSISTANT_MANAGE.getPostCount() - postCount;
            commentCountForPromotion = Rating.ASSISTANT_MANAGE.getCommentCount() - commentCount;

        }
        else if (postCount < Rating.DEPARTMENT_HEAD.getPostCount() || commentCount < Rating.DEPARTMENT_HEAD.getCommentCount()) {
            currentRating = Rating.ASSISTANT_MANAGE;
            nextRating = Rating.DEPARTMENT_HEAD;
            postCountForPromotion = Rating.DEPARTMENT_HEAD.getPostCount() - postCount;
            commentCountForPromotion = Rating.DEPARTMENT_HEAD.getCommentCount() - commentCount;
        }
        else if (postCount < Rating.MANAGING_DIRECTOR.getPostCount() || commentCount < Rating.MANAGING_DIRECTOR.getCommentCount()) {
            currentRating = Rating.DEPARTMENT_HEAD;
            nextRating = Rating.MANAGING_DIRECTOR;
            postCountForPromotion = Rating.MANAGING_DIRECTOR.getPostCount() - postCount;
            commentCountForPromotion = Rating.MANAGING_DIRECTOR.getCommentCount() - commentCount;
        }
        else if (postCount < Rating.BOSS.getPostCount() || commentCount < Rating.BOSS.getCommentCount()) {
            currentRating = Rating.MANAGING_DIRECTOR;
            nextRating = Rating.BOSS;
            postCountForPromotion = Rating.BOSS.getPostCount() - postCount;
            commentCountForPromotion = Rating.BOSS.getCommentCount() - commentCount;
        }
        else {
            currentRating = Rating.BOSS;
            nextRating = Rating.BOSS;

        }

        postCountForPromotion = Math.max(postCountForPromotion, 0);
        commentCountForPromotion = Math.max(commentCountForPromotion, 0);


        return RatingForPromotion.builder()
                .currentRating(currentRating)
                .nextRating(nextRating)
                .commentCountForPromotion(commentCountForPromotion)
                .postCountForPromotion(postCountForPromotion)
                .progress(makeProgress(postCountForPromotion, commentCountForPromotion, nextRating))
                .build();
    }

    private float makeProgress(int postCount, int commentCount, Rating rating) {
        if (rating == Rating.BOSS) return 1;
        
        int postCountForPromotion = rating.getPostCount();
        int commentCountForPromotion = rating.getCommentCount();

        return (float)((postCountForPromotion - postCount) + (commentCountForPromotion - commentCount)) / (float)(postCountForPromotion + commentCountForPromotion);
    }
}
