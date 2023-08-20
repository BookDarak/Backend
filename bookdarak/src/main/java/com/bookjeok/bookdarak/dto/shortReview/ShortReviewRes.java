package com.bookjeok.bookdarak.dto.shortReview;

import com.bookjeok.bookdarak.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Builder
@Getter @AllArgsConstructor
public class ShortReviewRes {
    private Long reviewId;
    private Long userId;
    private String username;
    private Long bookId;
    private String bookImgUrl;
    private String content;
    private BigDecimal rating;
    private Integer likeCount;
    private LocalDate createdDate;

    public static ShortReviewRes of(Review review) {
        return ShortReviewRes.builder()
                .reviewId(review.getId())
                .userId(review.getUser().getId())
                .username(review.getUser().getName())
                .bookId(review.getBook().getId())
                .bookImgUrl(review.getBook().getImgUrl())
                .content(review.getContent())
                .rating(review.getRating())
                .likeCount(review.getLikeCount())
                .createdDate(review.getCreatedAt().toLocalDate())
                .build();
    }

    // Review, User, Book에서 필요한 정보 꺼내서 shortReview 리스트로 반환
    public static List<ShortReviewRes> extractShortReviews(List<Review> reviews){
        List<ShortReviewRes> shortReviewList = new ArrayList<>();

        for (Review review : reviews) {
            shortReviewList.add(of(review));
        }
        return shortReviewList;
    }
}
