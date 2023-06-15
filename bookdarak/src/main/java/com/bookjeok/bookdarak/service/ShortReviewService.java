package com.bookjeok.bookdarak.service;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.base.BaseResponseStatus;
import com.bookjeok.bookdarak.domain.Book;
import com.bookjeok.bookdarak.domain.Review;
import com.bookjeok.bookdarak.domain.User;
import com.bookjeok.bookdarak.dto.shortReview.ShortReviewRes;
import com.bookjeok.bookdarak.repository.BookRepository;
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
            return new BaseResponse<>(BaseResponseStatus.NOT_EXIST_REVIEW);
        }

        List<ShortReviewRes> shortReviews = ShortReviewRes.extractShortReviews(reviews);

        return new BaseResponse<>(shortReviews);
    }

    public BaseResponse<List<ShortReviewRes>> getBookPublicReviews(Long bookId, String orderCriteria){
        // 책 조회
        if (!bookRepository.existsById(bookId)) {
            return new BaseResponse<>(BaseResponseStatus.NOT_EXIST_BOOK_ID);
        }
        Book book = bookRepository.findById(bookId).orElseThrow();

        // 정렬 기준에 맞는 리뷰 조회
        List<Review> reviews = new ArrayList<>();

        if (orderCriteria.equals("likeCount")){
            reviews = shortReviewRepository.findByBookAndPublicYnOrderByLikeCountDesc(book,"Y");
        }
        if (orderCriteria.equals("updatedAt")){
            reviews = shortReviewRepository.findByBookAndPublicYnOrderByUpdatedAtDesc(book,"Y");
        }

        if (reviews.isEmpty()){
            return new BaseResponse<>(BaseResponseStatus.NOT_EXIST_REVIEW);
        }
        System.out.println(reviews);
        List<ShortReviewRes> shortReviewRes = ShortReviewRes.extractShortReviews(reviews);

        return new BaseResponse<>(shortReviewRes);
    }

    public BaseResponse<List<ShortReviewRes>> getUserReviews(Long userId, String owner){
        System.out.println(owner);
        List<Review> reviews = new ArrayList<>();
        if (!userRepository.existsById(userId)) {
            return new BaseResponse<>(BaseResponseStatus.NOT_EXIST_USER_ID);
        }
        User user = userRepository.findById(userId).orElseThrow();

        if (owner.equals("Y")){
            reviews = shortReviewRepository.findByUser(user);
        }
        if (owner.equals("N")){
            reviews = shortReviewRepository.findByUserAndPublicYn(user, "Y");

        }
        System.out.println(reviews);
        if (reviews.isEmpty()){
            return new BaseResponse<>(BaseResponseStatus.NOT_EXIST_REVIEW);
        }

        List<ShortReviewRes> shortReviews = ShortReviewRes.extractShortReviews(reviews);

        return new BaseResponse<>(shortReviews);
    }
}
