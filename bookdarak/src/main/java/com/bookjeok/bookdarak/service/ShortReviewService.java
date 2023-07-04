package com.bookjeok.bookdarak.service;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.base.BaseResponseStatus;
import com.bookjeok.bookdarak.domain.Book;
import com.bookjeok.bookdarak.domain.Review;
import com.bookjeok.bookdarak.domain.User;
import com.bookjeok.bookdarak.dto.shortReview.ShortReviewRes;
import com.bookjeok.bookdarak.repository.BookRepository;
import com.bookjeok.bookdarak.repository.ReviewRepository;
import com.bookjeok.bookdarak.repository.ShortReviewRepository;
import com.bookjeok.bookdarak.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

import static com.bookjeok.bookdarak.base.BaseResponseStatus.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ShortReviewService {
    private final ShortReviewRepository shortReviewRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public BaseResponse<List<ShortReviewRes>> getAllPublicReviews(String orderCriteria) {
        List<Review> reviews = new ArrayList<>();

        // 정렬 기준에 맞는 리뷰 조회
        if (orderCriteria.equals("likeCount")) {
            reviews = shortReviewRepository.findByPublicYnOrderByLikeCountDesc("Y");

        }
        if (orderCriteria.equals("latest")){
            reviews = shortReviewRepository.findByPublicYnOrderByUpdatedAtDesc("Y");
        }

        if (reviews.isEmpty()){
            return new BaseResponse<>(NOT_EXIST_REVIEW);
        }

        List<ShortReviewRes> shortReviews = ShortReviewRes.extractShortReviews(reviews);

        return new BaseResponse<>(shortReviews);
    }

    public BaseResponse<List<ShortReviewRes>> getBookPublicReviews(Long bookId, String orderCriteria){
        // 책 조회
        if (!bookRepository.existsById(bookId)) {
            return new BaseResponse<>(NOT_EXIST_BOOK_ID);
        }
        Book book = bookRepository.findById(bookId).orElseThrow();

        // 정렬 기준에 맞는 리뷰 조회
        List<Review> reviews = new ArrayList<>();

        if (orderCriteria.equals("likeCount")){
            reviews = shortReviewRepository.findByBookAndPublicYnOrderByLikeCountDesc(book,"Y");
        }
        if (orderCriteria.equals("latest")){
            reviews = shortReviewRepository.findByBookAndPublicYnOrderByUpdatedAtDesc(book,"Y");
        }

        if (reviews.isEmpty()){
            return new BaseResponse<>(NOT_EXIST_REVIEW);
        }
        List<ShortReviewRes> shortReviewRes = ShortReviewRes.extractShortReviews(reviews);

        return new BaseResponse<>(shortReviewRes);
    }

    public BaseResponse<List<ShortReviewRes>> getUserReviews(Long userId, String owner){
        System.out.println(owner);
        List<Review> reviews = new ArrayList<>();
        if (!userRepository.existsById(userId)) {
            return new BaseResponse<>(NOT_EXIST_USER_ID);
        }
        User user = userRepository.findById(userId).orElseThrow();

        if (owner.equals("Y")){
            reviews = shortReviewRepository.findByUser(user);
        }
        if (owner.equals("N")){
            reviews = shortReviewRepository.findByUserAndPublicYn(user, "Y");

        }
        if (reviews.isEmpty()){
            return new BaseResponse<>(NOT_EXIST_REVIEW);
        }

        List<ShortReviewRes> shortReviews = ShortReviewRes.extractShortReviews(reviews);

        return new BaseResponse<>(shortReviews);
    }

    @Transactional
    public BaseResponse<String> recommendShortReview(Long userId, Long reviewId)
    {
        Review review = shortReviewRepository.findById(reviewId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

//        if(shortReviewRepository.existsUserAndReview(user, review)){
//            return new BaseResponse<>(RECOMMEND_ALREADY_ADDED);
//        }

        review.addReviewCount();


        return new BaseResponse<>("리뷰를 추천했습니다");
    }
}
