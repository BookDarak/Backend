package com.bookjeok.bookdarak.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReviewRes {
    @Getter
    @AllArgsConstructor
    public static class AddReviewRes{
        private Long createdReviewId;
    }

    @Getter @AllArgsConstructor
    public static class GetReviewRes{
        private BigDecimal rating;
        private String content;
        private String phrase;
        private Character publicYn;
        private int likeCount;
        private LocalDate startDate;
        private LocalDate endDate;
    }
}
