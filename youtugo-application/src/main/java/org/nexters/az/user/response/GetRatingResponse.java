package org.nexters.az.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.nexters.az.user.dto.RatingForPromotion;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetRatingResponse {
    private RatingForPromotion ratingForPromotion;
}
