package com.bookjeok.bookdarak.service;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.base.BaseResponseStatus;
import com.bookjeok.bookdarak.domain.Book;
import com.bookjeok.bookdarak.domain.BookReview;
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

        BookReview bookReview = reviewRepository.save(new BookReview(user, book, request.getRating(), request.getContent(),
            request.getPhrase(), request.getPublicYn(), request.getStartDate(), request.getEndDate()));

        return new BaseResponse<>(new ReviewRes.AddReviewRes(bookReview.getId()));
    }

    //시작일, 종료일 크기 비교
    public boolean isValidDateInterval(LocalDate stDate, LocalDate edDate){
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

        return (compare > 0);
    }
}
