package com.bookjeok.bookdarak.service;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.domain.Review;
import com.bookjeok.bookdarak.domain.User;
import com.bookjeok.bookdarak.dto.shortReview.ShortReviewRes;
import com.bookjeok.bookdarak.repository.ShortReviewRepository;
import com.bookjeok.bookdarak.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShortReviewService {
    private final ShortReviewRepository shortReviewRepository;
    private final UserRepository userRepository;

    public BaseResponse<List<ShortReviewRes>> getAllPublicReviews(String orderCriteria) {
        List<Review> reviews = new ArrayList<>();

        // 정렬 기준에 맞는 리뷰 조회
        if (orderCriteria.equals("likeCount")) {
            reviews = shortReviewRepository.findByPublicYnOrderByLikeCountDesc("Y");

        }
        if (orderCriteria.equals("latest")){
            reviews = shortReviewRepository.findByPublicYnOrderByUpdatedAtDesc("Y");
        }

        List<ShortReviewRes> shortReviews = ShortReviewRes.extractShortReviews(reviews);

        return new BaseResponse<>(shortReviews);
    }

    public BaseResponse<List<ShortReviewRes>> getUserReviews(Long userId, String owner){
        List<Review> reviews = new ArrayList<>();

        User user = userRepository.findById(userId).orElseThrow();

        if (owner.equals("Y")){
            reviews = shortReviewRepository.findByUser(user);
        }
        if (owner.equals("N")){
            reviews = shortReviewRepository.findByUserAndPublicYn(user, "Y");
        }

        List<ShortReviewRes> shortReviews = ShortReviewRes.extractShortReviews(reviews);

        return new BaseResponse<>(shortReviews);

    }
}
