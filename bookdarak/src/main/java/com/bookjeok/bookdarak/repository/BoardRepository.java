package com.bookjeok.bookdarak.repository;

import com.bookjeok.bookdarak.domain.Board;
import com.bookjeok.bookdarak.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    void deleteByBook(Book book);

    Board findByBook(Book book);
}
