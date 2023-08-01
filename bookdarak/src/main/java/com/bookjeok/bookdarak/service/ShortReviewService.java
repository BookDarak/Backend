package com.bookjeok.bookdarak.service;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.base.PageResponse;
import com.bookjeok.bookdarak.domain.*;
import com.bookjeok.bookdarak.dto.book.BookmarkRes;
import com.bookjeok.bookdarak.dto.shortReview.ShortReviewRes;
import com.bookjeok.bookdarak.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.bookjeok.bookdarak.base.BaseResponseStatus.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ShortReviewService {
    private final ShortReviewRepository shortReviewRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ReviewLikeRepository reviewLikerepository;

    public BaseResponse<PageResponse<ShortReviewRes>> getAllPublicReviews(Pageable pageable) {
        // 페이징된 데이터 조회
        Page<Review> pagedReviews = shortReviewRepository.findByPublicYn("Y", pageable);

        //DTO 변환 함수
        PageResponse<ShortReviewRes> pageResponse = getShortReviewResPageResponse(pagedReviews);

        return new BaseResponse<>(pageResponse);
    }

    public BaseResponse<PageResponse<ShortReviewRes>> getBookPublicReviews(Long bookId, Pageable pageable){
        // 책 조회
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book==null) {
            return new BaseResponse<>(NOT_EXIST_BOOK_ID);
        }

        Page<Review> pagedReviews = shortReviewRepository.findByPublicYnAndBook("Y", book, pageable);
        PageResponse<ShortReviewRes> pageResponse = getShortReviewResPageResponse(pagedReviews);

        return new BaseResponse<>(pageResponse);
    }

    public BaseResponse<PageResponse<ShortReviewRes>> getUserReviews(Long userId, String isOwner, Pageable pageable){
        User user = userRepository.findById(userId).orElse(null);

        if (user==null) {
            return new BaseResponse<>(NOT_EXIST_USER_ID);
        }

        Page<Review> pagedReviews;
        if (isOwner.equals("Y")){
            pagedReviews = shortReviewRepository.findByUser(user, pageable);
        } else{
            pagedReviews = shortReviewRepository.findByUserAndPublicYn(user, "Y",pageable);
        }

        PageResponse<ShortReviewRes> pageResponse = getShortReviewResPageResponse(pagedReviews);

        return new BaseResponse<>(pageResponse);
    }

    //서평 추천
    @Transactional
    public BaseResponse<String> likeShortReview(Long userId, Long reviewId)
    {
        if (validateId(userId, reviewId)!=null){
            return validateId(userId, reviewId);
        }
        Review review = shortReviewRepository.findById(reviewId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        if(reviewLikerepository.existsReviewLikeByUserAndReview(user, review)){
            return new BaseResponse<>(RECOMMEND_ALREADY_ADDED);
        }

        ReviewLike review_like = ReviewLike.builder().review(review).user(user).build();
        reviewLikerepository.save(review_like);

        review.addReviewCount();
        return new BaseResponse<>("요약서평을 추천했습니다");
    }
    //서평 추천 삭제
    @Transactional
    public BaseResponse<String> deleteShortReviewLike(Long userId, Long reviewId){
        if (validateId(userId, reviewId)!=null){
            return validateId(userId, reviewId);
        }
        Review review = shortReviewRepository.findById(reviewId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        ReviewLike reviewLike = reviewLikerepository.findReviewLikeByUserAndReview(user,review);
        if (reviewLike == null){
            return new BaseResponse<>(RECOMMEND_ALREADY_DELETED);
        }

        review.deleteReviewCount();
        reviewLikerepository.delete(reviewLike);

        return new BaseResponse<>("요약서평 추천을 삭제했습니다.");

    }

    //서평 추천 여부 조회
    public BaseResponse<String> getShortReviewLikeStatus(Long userId, Long reviewId) {

        if (validateId(userId, reviewId)!=null){
            return validateId(userId, reviewId);
        }

        User user = userRepository.findById(userId).orElseThrow();
        Review review = shortReviewRepository.findById(reviewId).orElseThrow();

        log.info("1");
        log.info(review.getContent());

        if(reviewLikerepository.existsReviewLikeByUserAndReview(user,review)){
            return new BaseResponse<>("true");
        } else {
            return new BaseResponse<>("false");
        }
    }


    // 서평 추천 수 조회
    public BaseResponse<String> getShortReviewLikeCount(Long reviewId) {
        Review review = shortReviewRepository.findById(reviewId).orElseThrow();
        if (!shortReviewRepository.existsById(reviewId)) {
            return new BaseResponse<>(NOT_EXIST_REVIEW_ID);
        }
        else{
            return new BaseResponse<>(String.valueOf(review.getLikeCount()));
        }
    }

    private static PageResponse<ShortReviewRes> getShortReviewResPageResponse(Page<Review> pagedReviews) {
        // ShortReviewRes -> Page<ShortReviewRes>
        Page<ShortReviewRes> shortReviewsPage = pagedReviews.map(ShortReviewRes::of);

        // Page->PageResponse
        return PageResponse.fromPage(shortReviewsPage);
    }

    public BaseResponse<String> validateId(Long userId, Long reviewId) {
        if (!userRepository.existsById(userId)) {
            return new BaseResponse<>(NOT_EXIST_USER_ID);
        }
        if (!shortReviewRepository.existsById(reviewId)) {
            return new BaseResponse<>(NOT_EXIST_REVIEW_ID);
        }
        return null;
    }
}