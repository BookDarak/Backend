package com.bookjeok.bookdarak.repository;

import com.bookjeok.bookdarak.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Boolean existsBookByNameAndPublisher(String name, String publisher);
    Book findBookByNameAndPublisher(String name,String publisher);
}
