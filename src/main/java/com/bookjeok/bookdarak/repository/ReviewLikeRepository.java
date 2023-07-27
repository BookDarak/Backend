package com.bookjeok.bookdarak.repository;

import com.bookjeok.bookdarak.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {
    //서평 좋아요
    Boolean existsReviewLikeByUserAndReview(User user, Review review);
    ReviewLike findReviewLikeByUserAndReview(User user, Review review);
}
