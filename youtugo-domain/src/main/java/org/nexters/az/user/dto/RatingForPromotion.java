package org.nexters.az.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.nexters.az.user.entity.Rating;

@AllArgsConstructor
@Getter
@Builder
public class RatingForPromotion {
    private Rating currentRating;
    private Rating nextRating;
    private int postCountForPromotion;
    private int commentCountForPromotion;
}
