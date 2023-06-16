package com.bookjeok.bookdarak.service;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.base.BaseResponseStatus;
import com.bookjeok.bookdarak.domain.Book;
import com.bookjeok.bookdarak.domain.Bookmark;
import com.bookjeok.bookdarak.domain.User;
import com.bookjeok.bookdarak.repository.BookRepository;
import com.bookjeok.bookdarak.repository.BookmarkRepository;
import com.bookjeok.bookdarak.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            return new BaseResponse<>(BaseResponseStatus.BOOKMARK_ALREADY_ADDED);
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

        bookmarkRepository.deleteBookmarkByUserAndBook(user, book);

        return new BaseResponse<>("북마크를 삭제했습니다.");

    }

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

    public BaseResponse<String> validateId(Long userId, Long bookId){
        if (!userRepository.existsById(userId)){
            return new BaseResponse<>(BaseResponseStatus.NOT_EXIST_USER_ID);
        }
        if (!bookRepository.existsById(bookId)){
            return new BaseResponse<>(BaseResponseStatus.NOT_EXIST_BOOK_ID);
        }
        return null;
    }

}
