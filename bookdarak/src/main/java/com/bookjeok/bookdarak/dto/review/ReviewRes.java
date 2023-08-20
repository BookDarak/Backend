package com.bookjeok.bookdarak.dto.review;

import com.bookjeok.bookdarak.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
        private String name;
        private String author;
        private String bookImgUrl;

        private BigDecimal rating;
        private String content;
        private String phrase;
        private String publicYn;
        private int likeCount;
        private LocalDate startDate;
        private LocalDate endDate;


        public GetReviewRes(Review review) {
            this.name = review.getBook().getName();
            this.author = review.getBook().getAuthor();
            this.bookImgUrl = review.getBook().getImgUrl();
            this.rating = review.getRating();
            this.content = review.getContent();
            this.phrase = review.getPhrase();
            this.publicYn = review.getPublicYn();
            this.likeCount = review.getLikeCount();
            this.startDate = review.getStartDate();
            this.endDate = review.getEndDate();
        }
    }

    @Getter
    public static class Calendar{
        private final String name;
        private final String author;
        private final String bookImgUrl;
        private final Long reviewId;
        private final LocalDate startDate;
        private final LocalDate endDate;

        public Calendar(Review review) {
            this.name = review.getBook().getName();
            this.author = review.getBook().getAuthor();
            this.bookImgUrl = review.getBook().getImgUrl();
            this.reviewId = review.getId();
            this.startDate = review.getStartDate();
            this.endDate = review.getEndDate();
        }
    }
}
