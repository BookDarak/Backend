package com.bookjeok.bookdarak.repository;

import com.bookjeok.bookdarak.domain.Book;
import com.bookjeok.bookdarak.domain.Bookmark;
import com.bookjeok.bookdarak.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
   Boolean existsByUserAndBook(User user, Book book);
   Bookmark findByUserAndBook(User user, Book book);
   Page<Bookmark> findByUser(User user, Pageable pageable);
   Long countByUser(User user);
   void deleteByUser(User user);
   void deleteByBook(Book book);
   List<Bookmark> findByUser(User user);
}
