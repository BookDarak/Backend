package com.bookjeok.bookdarak.service;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.domain.*;
import com.bookjeok.bookdarak.dto.shortReview.ShortReviewRes;
import com.bookjeok.bookdarak.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ReviewLikeRepository reviewLikerepository;

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

    public BaseResponse<List<ShortReviewRes>> getBookPublicReviews(Long reviewId, String orderCriteria){
        // 책 조회
        Book book = bookRepository.findById(reviewId).orElse(null);

        if (book==null) {
            return new BaseResponse<>(NOT_EXIST_BOOK_ID);
        }

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
        User user = userRepository.findById(userId).orElse(null);

        if (user==null) {
            return new BaseResponse<>(NOT_EXIST_USER_ID);
        }

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
        Review review = shortReviewRepository.findById(reviewId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

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