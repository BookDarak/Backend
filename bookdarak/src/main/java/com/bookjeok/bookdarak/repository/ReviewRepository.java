package com.bookjeok.bookdarak.repository;

import com.bookjeok.bookdarak.domain.Book;
import com.bookjeok.bookdarak.domain.Review;
import com.bookjeok.bookdarak.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findByUserAndBook(User user, Book book);
    boolean existsByUserAndBook(User user, Book book);
    void deleteByUser(User user);
    void deleteByBook(Book book);
    Long countByUser(User user);

    @Query(value = "select r from Review r where"+
            " r.user = :user and ((r.startDate between :calStartD and :calEndD) or (r.endDate between : calStartD and :calEndD))")
    List<Review> findByDate(@Param("user")User user, @Param("calStartD")LocalDate calStartD, @Param("calEndD")LocalDate calEndD);
}
