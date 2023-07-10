package com.bookjeok.bookdarak.dto.review;

import com.bookjeok.bookdarak.domain.Review;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReviewRes {
    @Getter
    @AllArgsConstructor
    public static class ReviewId {
        private Long reviewId;
    }

    @Getter @AllArgsConstructor
    public static class GetReviewRes{
        private BigDecimal rating;
        private String content;
        private String phrase;
        private String publicYn;
        private int likeCount;
        private LocalDate startDate;
        private LocalDate endDate;


        public GetReviewRes(Review review) {
            this.rating = review.getRating();
            this.content = review.getContent();
            this.phrase = review.getPhrase();
            this.publicYn = review.getPublicYn();
            this.likeCount = review.getLikeCount();
            this.startDate = review.getStartDate();
            this.endDate = review.getEndDate();
        }
    }
}
