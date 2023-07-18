package com.bookjeok.bookdarak.repository;

import com.bookjeok.bookdarak.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Review_likeRepository extends JpaRepository<Review_like, Long> {
    //서평 좋아요
    Boolean existsReview_likeByUserAndReview(User user, Review review);
    Review_like findReview_likeByUserAndReview(User user, Review review);
}
