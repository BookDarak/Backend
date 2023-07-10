package com.bookjeok.bookdarak.repository;

import com.bookjeok.bookdarak.domain.Book;
import com.bookjeok.bookdarak.domain.Review;
import com.bookjeok.bookdarak.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findByUserAndBook(User user, Book book);
    boolean existsByUserAndBook(User user, Book book);
    void deleteReviewsByUser(User user);
    Long countByUser(User user);
}
