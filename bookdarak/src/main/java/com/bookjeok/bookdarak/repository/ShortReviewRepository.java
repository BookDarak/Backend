package com.bookjeok.bookdarak.repository;

import com.bookjeok.bookdarak.domain.Book;
import com.bookjeok.bookdarak.domain.Review;
import com.bookjeok.bookdarak.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShortReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByPublicYnOrderByLikeCountDesc(String publicYn);
    List<Review> findByPublicYnOrderByUpdatedAtDesc(String publicYn);

    List<Review> findByBookAndPublicYnOrderByLikeCountDesc(Book book, String publicYn);

    List<Review> findByBookAndPublicYnOrderByUpdatedAtDesc(Book book, String publicYn);
    List<Review> findByUser(User user);
    List<Review> findByUserAndPublicYn(User user, String publicYn);
}
