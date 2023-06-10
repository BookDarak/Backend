package com.bookjeok.bookdarak.dto.shortReview;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


public class ShortReviewRes {
    @Builder
    @Getter @AllArgsConstructor
    public static class publicReviews{
        private Long userId;
        private String username;
        private Long bookId;
        private String bookImgUrl;
        private BigDecimal rating;
        private int likeCount;
        private LocalDate updatedDate;
    }
}
