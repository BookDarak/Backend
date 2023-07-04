package com.bookjeok.bookdarak.service;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.base.BaseResponseStatus;
import com.bookjeok.bookdarak.domain.Book;
import com.bookjeok.bookdarak.domain.Review;
import com.bookjeok.bookdarak.domain.User;
import com.bookjeok.bookdarak.dto.review.ReviewReq;
import com.bookjeok.bookdarak.dto.review.ReviewRes;
import com.bookjeok.bookdarak.repository.BookRepository;
import com.bookjeok.bookdarak.repository.ReviewRepository;
import com.bookjeok.bookdarak.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.bookjeok.bookdarak.base.BaseResponseStatus.*;


@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public BaseResponse<ReviewRes.ReviewId> addReview(Long userId, Long bookId, ReviewReq.AddReviewReq request)  {
        //유저와 도서 조회
        if (!userRepository.existsById(userId)) {
            return new BaseResponse<>(NOT_EXIST_USER_ID);
        }
        if (!bookRepository.existsById(bookId)){
            return new BaseResponse<>(NOT_EXIST_BOOK_ID);
        }
        User user = userRepository.findById(userId).orElseThrow();
        Book book = bookRepository.findById(bookId).orElseThrow();

        //유저id, 도서id를 비교해 기존 서평 있는지 체크
        if (reviewRepository.existsByUserAndBook(user,book)){
            return new BaseResponse<>(REVIEW_ALREADY_EXISTS);
        }

        //없으면 서평 저장
        Review review = reviewRepository.save(new Review(user, book, request));

        return new BaseResponse<>(new ReviewRes.ReviewId(review.getId()));
    }

    @Transactional(readOnly = true)
    public BaseResponse<ReviewRes.GetReviewRes> getReview(Long reviewId) {
        if (!reviewRepository.existsById(reviewId)){
            return new BaseResponse<>(NOT_EXIST_REVIEW);
        }
        Review review = findReviewById(reviewId);

        return new BaseResponse<>(new ReviewRes.GetReviewRes(review));
    }

    public BaseResponse<BaseResponseStatus> updateReview(ReviewReq.UpdateReviewReq request, Long reviewId){
        if (!reviewRepository.existsById(reviewId)){
            return new BaseResponse<>(NOT_EXIST_REVIEW);
        }
        Review review = findReviewById(reviewId);
        review.updateReview(request);
        return new BaseResponse<>(UPDATE_SUCCESS);
    }

    public BaseResponse<String> deleteReview(Long reviewId){
        if (!reviewRepository.existsById(reviewId)){
            return new BaseResponse<>(NOT_EXIST_REVIEW);
        }
        Review review = findReviewById(reviewId);
        reviewRepository.delete(review);
        return new BaseResponse<>("삭제를 완료했습니다.");
    }
    public Review findReviewById(Long reviewId){
        return reviewRepository.findById(reviewId).orElseThrow();
    }
}