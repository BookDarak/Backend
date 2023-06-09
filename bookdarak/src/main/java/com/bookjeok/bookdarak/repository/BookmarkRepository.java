package com.bookjeok.bookdarak.repository;

import com.bookjeok.bookdarak.domain.Book;
import com.bookjeok.bookdarak.domain.Bookmark;
import com.bookjeok.bookdarak.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
   Boolean existsByUserAndBook(User user, Book book);
   Bookmark findBookmarkByUserAndBook(User user, Book book);
   List<Bookmark> findAllByUser(User user);
   Long countByUser(User user);
   void deleteBookmarksByUser(User user);
}
