package com.bookjeok.bookdarak.service;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.base.BaseResponseStatus;
import com.bookjeok.bookdarak.domain.Book;
import com.bookjeok.bookdarak.dto.book.BookReq;
import com.bookjeok.bookdarak.dto.book.BookRes;
import com.bookjeok.bookdarak.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.bookjeok.bookdarak.base.BaseResponseStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public BaseResponse<BookRes.BookId> findBookByIsbn(BookReq.bookInfo request){
        /*
        받은 정보 중 isbn으로 책 검색
        책 있으면 책 아이디 반환
        책 없으면 책 저장 후 아이디 반환
         */
        Book book = bookRepository.findBookByIsbn(request.getIsbn());

        if (book == null){
            book = bookRepository.save(new Book(request));
        }
        return new BaseResponse<>(new BookRes.BookId(book.getId()));
    }

    @Transactional(readOnly = true)
    public BaseResponse<BookRes.BookInfo> getBookInfo(Long id){
        if (!bookRepository.existsById(id)){
            return new BaseResponse<>(NOT_EXIST_BOOK_ID);
        }
        Book book = bookRepository.findById(id).orElseThrow();

        return new BaseResponse<>(new BookRes.BookInfo(book));
    }
}