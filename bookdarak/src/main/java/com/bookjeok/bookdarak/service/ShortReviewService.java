package com.bookjeok.bookdarak.service;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.domain.Review;
import com.bookjeok.bookdarak.dto.shortReview.ShortReviewRes;
import com.bookjeok.bookdarak.repository.ShortReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ShortReviewService {
    private final ShortReviewRepository shortReviewRepository;

    @Transactional(readOnly = true)
    public BaseResponse<List<ShortReviewRes.publicReviews>> getPublicReviews(String orderCriteria) {
        List<Review> reviews = new ArrayList<>();
        List<ShortReviewRes.publicReviews> shortReviewList = new ArrayList<>();

        if (orderCriteria.equals("likeCount")) {
            reviews = shortReviewRepository.findByPublicYnOrderByLikeCountDesc("Y");

        }
        if (orderCriteria.equals("latest")){
            reviews = shortReviewRepository.findByPublicYnOrderByUpdatedAtDesc("Y");
        }

        for (Review review : reviews) {// 리뷰 꺼냄
            ShortReviewRes.publicReviews dto = ShortReviewRes.publicReviews.builder()
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
        return new BaseResponse<>(shortReviewList);
    }
}
