package com.bookjeok.bookdarak.service;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.domain.Book;
import com.bookjeok.bookdarak.domain.Bookmark;
import com.bookjeok.bookdarak.domain.User;
import com.bookjeok.bookdarak.dto.book.BookmarkRes;
import com.bookjeok.bookdarak.repository.BookRepository;
import com.bookjeok.bookdarak.repository.BookmarkRepository;
import com.bookjeok.bookdarak.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bookjeok.bookdarak.base.BaseResponseStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    public BaseResponse<String> addBookmark(Long userId, Long bookId){
        if (validateId(userId, bookId)!=null){
            return validateId(userId, bookId);
        }
        User user = userRepository.findById(userId).orElseThrow();
        Book book = bookRepository.findById(bookId).orElseThrow();

        if (bookmarkRepository.existsByUserAndBook(user,book)){
            return new BaseResponse<>(BOOKMARK_ALREADY_ADDED);
        }
        bookmarkRepository.save(Bookmark.builder()
                .user(user)
                .book(book)
                .bookmarkYn("Y")
                .build());

        return new BaseResponse<>("북마크에 추가했습니다.");
    }

    public BaseResponse<String> deleteBookmark(Long userId, Long bookId){
        if (validateId(userId, bookId)!=null){
            return validateId(userId, bookId);
        }
        User user = userRepository.findById(userId).orElseThrow();
        Book book = bookRepository.findById(bookId).orElseThrow();

        Bookmark bookmark = bookmarkRepository.findBookmarkByUserAndBook(user, book);
        if (bookmark == null){
            return new BaseResponse<>(BOOKMARK_ALREADY_DELETED);
        }
        bookmarkRepository.delete(bookmark);

        return new BaseResponse<>("북마크를 삭제했습니다.");

    }

    @Transactional(readOnly = true)
    public BaseResponse<String> getBookmarkStatus(Long userId, Long bookId) {

        if (validateId(userId, bookId)!=null){
            return validateId(userId, bookId);
        }
        User user = userRepository.findById(userId).orElseThrow();
        Book book = bookRepository.findById(bookId).orElseThrow();

        if (bookmarkRepository.existsByUserAndBook(user, book)) {
            return new BaseResponse<>("true");
        } else {
            return new BaseResponse<>("false");
        }

    }

    @Transactional(readOnly = true)
    public BaseResponse<List<BookmarkRes>> getUserBookmarks(Long userId) {
        if (!userRepository.existsById(userId)){
            return new BaseResponse<>(NOT_EXIST_USER_ID);
        }

        User user = userRepository.findById(userId).orElseThrow();
        List<Bookmark> bookmarks = bookmarkRepository.findAllByUser(user);

        if (bookmarks.isEmpty()){
            return new BaseResponse<>(BOOKMARK_NOT_EXISTS);
        }
        List<BookmarkRes> bookmarkResList = BookmarkRes.getBookmarkResList(bookmarks);


        return new BaseResponse<>(bookmarkResList);
    }

    public BaseResponse<String> validateId(Long userId, Long bookId){
        if (!userRepository.existsById(userId)){
            return new BaseResponse<>(NOT_EXIST_USER_ID);
        }
        if (!bookRepository.existsById(bookId)){
            return new BaseResponse<>(NOT_EXIST_BOOK_ID);
        }
        return null;
    }
}
