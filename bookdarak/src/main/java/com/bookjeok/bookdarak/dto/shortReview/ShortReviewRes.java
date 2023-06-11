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
    private Long userId;
    private String username;
    private Long bookId;
    private String bookImgUrl;
    private BigDecimal rating;
    private Integer likeCount;
    private LocalDate updatedDate;

    // Review, User, Book에서 필요한 정보 꺼내서 shortReview 리스트로 반환
    public static List<ShortReviewRes> extractShortReviews(List<Review> reviews){
        List<ShortReviewRes> shortReviewList = new ArrayList<>();

        for (Review review : reviews) {// 리뷰 꺼냄
            ShortReviewRes dto = ShortReviewRes.builder()
                    .userId(review.getUser().getId())
                    .username(review.getUser().getName())
                    .bookId(review.getBook().getId())
                    .bookImgUrl(review.getBook().getImgUrl())
                    .rating(review.getRating())
                    .likeCount(review.getLikeCount())
                    .updatedDate(review.getUpdatedAt().toLocalDate())
                    .build();
            shortReviewList.add(dto);
        }
        return shortReviewList;
    }


}
