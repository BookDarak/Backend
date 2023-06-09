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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public BaseResponse<ReviewRes.AddReviewRes> addReview(Long userId, Long bookId, ReviewReq.AddReviewReq request)  {
        if (!userRepository.existsById(userId)) {
            return new BaseResponse<>(BaseResponseStatus.NOT_EXIST_USER_ID);
        }
        if (!bookRepository.existsById(bookId)){
            return new BaseResponse<>(BaseResponseStatus.NOT_EXIST_BOOK_ID);
        }
        User user = userRepository.findById(userId).orElseThrow();
        Book book = bookRepository.findById(bookId).orElseThrow();

        if (reviewRepository.existsByUserAndBook(user,book)){
            return new BaseResponse<>(BaseResponseStatus.REVIEW_ALREADY_EXISTS);
        }

        Review review = reviewRepository.save(new Review(user, book, request.getRating(), request.getContent(),
            request.getPhrase(),  request.getPublicYn(), request.getStartDate(), request.getEndDate()));

        return new BaseResponse<>(new ReviewRes.AddReviewRes(review.getId()));
    }

    public BaseResponse<ReviewRes.GetReviewRes> getReview(Long userId, Long bookId) {
        Review review = reviewRepository.findReviewByUserIdAndBookId(userId, bookId);
        if (review == null){
            return new BaseResponse<>(BaseResponseStatus.NOT_EXIST_REVIEW);
        }
        return new BaseResponse<>(new ReviewRes.GetReviewRes(review.getRating(),review.getContent(),
                review.getPhrase(),review.getPublicYn(),review.getLikeCount(),review.getStartDate(),review.getEndDate()));
    }

    public BaseResponse<String> updateReview(ReviewReq.UpdateReviewReq request, Long userId, Long bookId){
        Review review = reviewRepository.findReviewByUserIdAndBookId(userId, bookId);
        if (review ==null){
            return new BaseResponse<>(BaseResponseStatus.NOT_EXIST_REVIEW);
        }
        review.updateReview(request);
        return new BaseResponse<>("서평을 성공적으로 수정했습니다.");
    }

    public BaseResponse<String> deleteReview(Long userId, Long bookId){
        Review review = reviewRepository.findReviewByUserIdAndBookId(userId, bookId);
        if (review == null){
            return new BaseResponse<>(BaseResponseStatus.NOT_EXIST_REVIEW);
        }
        reviewRepository.delete(review);
        return new BaseResponse<>("서평을 성공적으로 삭제했습니다.");
    }

    //시작일, 종료일 크기 비교
    public boolean isInValidDateInterval(LocalDate stDate, LocalDate edDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate;
        Date endDate;

        try {
            startDate = dateFormat.parse( String.valueOf(stDate) );
            endDate = dateFormat.parse( String.valueOf(edDate) );
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        int compare = startDate.compareTo( endDate );

        return (compare < 0);
    }

    public boolean isInValidPublicYn(String publicYn){
        return !(Objects.equals(publicYn, "Y") || Objects.equals(publicYn, "N"));
    }
}
