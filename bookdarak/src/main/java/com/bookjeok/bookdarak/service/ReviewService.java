package com.bookjeok.bookdarak.service;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.base.BaseResponseStatus;
import com.bookjeok.bookdarak.domain.Book;
import com.bookjeok.bookdarak.domain.Review;
import com.bookjeok.bookdarak.domain.ReviewLike;
import com.bookjeok.bookdarak.domain.User;
import com.bookjeok.bookdarak.dto.review.ReviewReq;
import com.bookjeok.bookdarak.dto.review.ReviewRes;
import com.bookjeok.bookdarak.repository.BookRepository;
import com.bookjeok.bookdarak.repository.ReviewLikeRepository;
import com.bookjeok.bookdarak.repository.ReviewRepository;
import com.bookjeok.bookdarak.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.bookjeok.bookdarak.base.BaseResponseStatus.*;


@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ReviewLikeRepository reviewLikeRepository;

    public BaseResponse<ReviewRes.ReviewId> addReview(Long userId, Long bookId, ReviewReq.AddReviewReq request)  {
        User user = findUserById(userId);
        Book book = findBookById(bookId);
        //유저와 도서 조회
        if (user==null) {
            return new BaseResponse<>(NOT_EXIST_USER_ID);
        }
        if (book==null){
            return new BaseResponse<>(NOT_EXIST_BOOK_ID);
        }

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
        Review review = findReviewById(reviewId);
        if (review == null) {
            return new BaseResponse<>(NOT_EXIST_REVIEW);
        }

        return new BaseResponse<>(new ReviewRes.GetReviewRes(review));
    }

    @Transactional(readOnly = true)
    public BaseResponse<ReviewRes.ReviewId> getReviewId(Long userId, Long bookId){
        User user = findUserById(userId);
        Book book = findBookById(bookId);
        if (user==null){
            return new BaseResponse<>(NOT_EXIST_USER_ID);
        }
        if (book==null){
            return new BaseResponse<>(NOT_EXIST_BOOK_ID);
        }
        if (reviewRepository.existsByUserAndBook(user, book)){
            Long reviewId = reviewRepository.findByUserAndBook(user,book).getId();
            return new BaseResponse<>(new ReviewRes.ReviewId(reviewId));
        } else {
            return new BaseResponse<>(new ReviewRes.ReviewId(-1L));
        }
    }
    public BaseResponse<BaseResponseStatus> updateReview(ReviewReq.UpdateReviewReq request, Long reviewId){

        Review review = findReviewById(reviewId);

        if (review == null) {
            return new BaseResponse<>(NOT_EXIST_REVIEW);
        }
        review.updateReview(request);
        return new BaseResponse<>(UPDATE_SUCCESS);
    }

    public BaseResponse<String> deleteReview(Long reviewId){
        Review review = findReviewById(reviewId);
        if (review==null){
            return new BaseResponse<>(NOT_EXIST_REVIEW);
        }
        reviewLikeRepository.deleteByReview(review); //연관관계 삭제
        reviewRepository.delete(review);
        return new BaseResponse<>("삭제를 완료했습니다.");
    }

    @Transactional(readOnly = true)
    public BaseResponse<List<ReviewRes.Calendar>> getCalendar(LocalDate startDate, LocalDate endDate, Long userId) {
        User user = findUserById(userId);
        if (user==null){
            return new BaseResponse<>(NOT_EXIST_USER_ID);
        }
        List<Review> result = reviewRepository.findByDate(user, startDate, endDate);

        List<ReviewRes.Calendar> lst = new ArrayList<>();
        for (Review r: result){
            ReviewRes.Calendar calendarRes = new ReviewRes.Calendar(r);
            lst.add(calendarRes);
        }
        return new BaseResponse<>(lst);
    }

    //
    public User findUserById(Long userId){return userRepository.findById(userId).orElse(null);}
    public Book findBookById(Long bookId){return bookRepository.findById(bookId).orElse(null);}
    public Review findReviewById(Long reviewId){
        return reviewRepository.findById(reviewId).orElse(null);
    }
}