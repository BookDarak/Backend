package com.bookjeok.bookdarak.repository;

import com.bookjeok.bookdarak.domain.Board;
import com.bookjeok.bookdarak.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

public interface BoardRepository extends JpaRepository<Board, Long> {
    void deleteByBook(Book book);

    Board findByBook(Book book);

    Page<Board> findAll(Pageable pageable);
}
