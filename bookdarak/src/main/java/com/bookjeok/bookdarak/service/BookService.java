package com.bookjeok.bookdarak.service;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.domain.Book;
import com.bookjeok.bookdarak.domain.Bookmark;
import com.bookjeok.bookdarak.domain.User;
import com.bookjeok.bookdarak.dto.book.BookReq;
import com.bookjeok.bookdarak.dto.book.BookRes;
import com.bookjeok.bookdarak.repository.BookRepository;
import com.bookjeok.bookdarak.repository.BookmarkRepository;
import com.bookjeok.bookdarak.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bookjeok.bookdarak.base.BaseResponseStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;

    public BaseResponse<BookRes.BookId> findBookByIsbn(BookReq.bookInfo request){
        /*
        받은 정보 중 isbn으로 책 검색
        책 있으면 책 아이디 반환
        책 없으면 책 저장 후 아이디 반환
         */
        Book book = bookRepository.findByIsbn(request.getIsbn());

        if (book == null){
            book = bookRepository.save(new Book(request));
        }
        return new BaseResponse<>(new BookRes.BookId(book.getId()));
    }

    @Transactional(readOnly = true)
    public BaseResponse<BookRes.BookInfo> getBookInfo(Long id){
        Book book = bookRepository.findById(id).orElse(null);

        if (book==null){
            return new BaseResponse<>(NOT_EXIST_BOOK_ID);
        }

        return new BaseResponse<>(new BookRes.BookInfo(book));
    }

    //연령별 책 추천
    public List<User> getUsersByAge(int age) {
        int lowerBound = age / 10 * 10;
        int upperBound = lowerBound + 9;
        return userRepository.findByAgeBetween(lowerBound, upperBound);
    }

    public List<Book> getBooksByUsersAge(int age) {
        List<User> users = getUsersByAge(age);
        // 조회된 유저들의 모든 북마크 정보를 가져옵니다.
        Map<Book, Integer> bookmarkCountMap = new HashMap<>();
        for (User user : users) {
            List<Bookmark> bookmarks = bookmarkRepository.findByUser(user);
            for (Bookmark bookmark : bookmarks) {
                Book book = bookmark.getBook();
                bookmarkCountMap.put(book, bookmarkCountMap.getOrDefault(book, 0) + 1);
            }
        }

        // 북마크된 횟수가 많은 순으로 정렬하여 상위 5개의 책을 얻습니다.
        List<Book> top5Books = new ArrayList<>(bookmarkCountMap.keySet());
        top5Books.sort((book1, book2) -> bookmarkCountMap.get(book2).compareTo(bookmarkCountMap.get(book1)));
        top5Books = top5Books.subList(0, Math.min(5, top5Books.size()));

        return top5Books;
    }

    @Transactional
    public BaseResponse<List<Book>> recommendBookByAge(Long userid){
        User user = userRepository.findById(userid). orElse(null);
        if (!userRepository.existsById(userid)) {
            return new BaseResponse<>(NOT_EXIST_USER_ID);
        }

        List<Book> books = getBooksByUsersAge(user.getAge());
        return new BaseResponse<>(books);
    }

    //성별 책 추천
    public List<Book> getBooksByUsersGender(String gender) {
        List<User> users = userRepository.findByGender(gender);
        // 조회된 유저들의 모든 북마크 정보를 가져옵니다.
        Map<Book, Integer> bookmarkCountMap = new HashMap<>();
        for (User user : users) {
            List<Bookmark> bookmarks = bookmarkRepository.findByUser(user);
            for (Bookmark bookmark : bookmarks) {
                Book book = bookmark.getBook();
                bookmarkCountMap.put(book, bookmarkCountMap.getOrDefault(book, 0) + 1);
            }
        }

        // 북마크된 횟수가 많은 순으로 정렬하여 상위 5개의 책을 얻습니다.
        List<Book> top5Books = new ArrayList<>(bookmarkCountMap.keySet());
        top5Books.sort((book1, book2) -> bookmarkCountMap.get(book2).compareTo(bookmarkCountMap.get(book1)));
        top5Books = top5Books.subList(0, Math.min(5, top5Books.size()));

        return top5Books;
    }
    @Transactional
    public BaseResponse<List<Book>> recommendBookByGender(Long userid){
        User user = userRepository.findById(userid). orElse(null);
        if (!userRepository.existsById(userid)) {
            return new BaseResponse<>(NOT_EXIST_USER_ID);
        }
        List<Book> books = getBooksByUsersGender(user.getGender());
        return new BaseResponse<>(books);
    }
}