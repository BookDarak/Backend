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
        private Character publicYn;
        private LocalDate startDate;
        private LocalDate endDate;
    }
}
