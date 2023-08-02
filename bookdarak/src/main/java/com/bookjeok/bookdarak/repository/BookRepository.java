package com.bookjeok.bookdarak.repository;

import com.bookjeok.bookdarak.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByIsbn(String isbn);
}
