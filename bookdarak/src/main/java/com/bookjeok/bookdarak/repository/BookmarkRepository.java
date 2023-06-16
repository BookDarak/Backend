package com.bookjeok.bookdarak.repository;

import com.bookjeok.bookdarak.domain.Book;
import com.bookjeok.bookdarak.domain.Bookmark;
import com.bookjeok.bookdarak.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
   Boolean existsByUserAndBook(User user, Book book);

    void deleteBookmarkByUserAndBook(User user, Book book);
}
