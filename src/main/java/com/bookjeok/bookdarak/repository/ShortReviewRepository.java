package com.bookjeok.bookdarak.repository;

import com.bookjeok.bookdarak.domain.Book;
import com.bookjeok.bookdarak.domain.Review;
import com.bookjeok.bookdarak.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShortReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByPublicYn(String publicYn, Pageable pageable);
    Page<Review> findByUser(User user, Pageable pageable);
    Page<Review> findByUserAndPublicYn(User user, String publicYn, Pageable pageable);

    Page<Review> findByPublicYnAndBook(String publicYn, Book book, Pageable pageable);
}
