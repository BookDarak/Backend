package com.bookjeok.bookdarak.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ReviewRes {
    @Getter
    @AllArgsConstructor
    public static class AddReviewRes{
        private Long createdReviewId;
    }
}
