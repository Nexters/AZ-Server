package org.nexters.az.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.nexters.az.user.entity.Rating;

@AllArgsConstructor
@Getter
public class RatingForPromotion {
    private Rating currentRating;
    private Rating nextRating;
    private int postCountForPromotion;
    private int commentCountForPromotion;
    private float progress;
    private String message;

    @Builder
    public RatingForPromotion(
        Rating currentRating,
        Rating nextRating,
        int postCountForPromotion,
        int commentCountForPromotion,
        float progress
    ) {
        this.currentRating = currentRating;
        this.nextRating = nextRating;
        this.postCountForPromotion = postCountForPromotion;
        this.commentCountForPromotion = commentCountForPromotion;
        this.message = currentRating.getMessage();
        this.progress = progress;
    }
}
