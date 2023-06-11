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
            return new BaseResponse<>(BaseResponseStatus.NOT_EXIST_USER_ID);
        }
        if (!bookRepository.existsById(bookId)){
            return new BaseResponse<>(BaseResponseStatus.NOT_EXIST_BOOK_ID);
        }
        User user = userRepository.findById(userId).orElseThrow();
        Book book = bookRepository.findById(bookId).orElseThrow();

        //유저id, 도서id를 비교해 기존 서평 있는지 체크
        if (reviewRepository.existsByUserAndBook(user,book)){
            return new BaseResponse<>(BaseResponseStatus.REVIEW_ALREADY_EXISTS);
        }

        //없으면 서평 저장
        Review review = reviewRepository.save(new Review(user, book, request));

        return new BaseResponse<>(new ReviewRes.ReviewId(review.getId()));
    }

    @Transactional(readOnly = true)
    public BaseResponse<ReviewRes.GetReviewRes> getReview(Long userId, Long bookId) {
        Review review = reviewRepository.findReviewByUserIdAndBookId(userId, bookId);
        if (review == null){
            return new BaseResponse<>(BaseResponseStatus.NOT_EXIST_REVIEW);
        }
        return new BaseResponse<>(new ReviewRes.GetReviewRes(review));
    }

    public BaseResponse<String> updateReview(ReviewReq.UpdateReviewReq request, Long userId, Long bookId){
        Review review = reviewRepository.findReviewByUserIdAndBookId(userId, bookId);
        if (review == null){
            return new BaseResponse<>(BaseResponseStatus.NOT_EXIST_REVIEW);
        }
        review.updateReview(request);
        return new BaseResponse<>("수정을 완료했습니다.");
    }

    public BaseResponse<String> deleteReview(Long userId, Long bookId){
        Review review = reviewRepository.findReviewByUserIdAndBookId(userId, bookId);
        if (review == null){
            return new BaseResponse<>(BaseResponseStatus.NOT_EXIST_REVIEW);
        }
        reviewRepository.delete(review);
        return new BaseResponse<>("삭제를 완료했습니다.");
    }
}