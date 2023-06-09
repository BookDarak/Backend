package com.bookjeok.bookdarak.dto.review;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReviewReq {
    @Getter
    public static class AddReviewReq{
        private BigDecimal rating;
        private String content;
        private String phrase;
        private String publicYn;
        private int likeCount;
        private LocalDate startDate;
        private LocalDate endDate;
    }

    @Getter
    public static class UpdateReviewReq{
        private BigDecimal rating;
        private String content;
        private String phrase;
        private String publicYn;
        private int likeCount;
        private LocalDate startDate;
        private LocalDate endDate;
    }
}
