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

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public BaseResponse<BookRes.BookFindRes> findBookByName(BookReq.FindBookReq request){
        /*
        받은 정보 중 책이름 & 출판사로 책 검색
        책 있으면 책 아이디 반환
        책 없으면 책 저장 후 아이디 반환
         */
        Book book = bookRepository.findBookByNameAndPublisher(request.getName(), request.getPublisher());

        String author = listToString(request.getAuthorList());
        if (book == null){
            book = bookRepository.save(new Book(request.getName(), author ,request.getPublisher(),
                    request.getPublishedDate(),request.getPrice(), request.getIntroduction(),request.getSiteUrl(),request.getImgUrl()));
        }
        return new BaseResponse<>(new BookRes.BookFindRes(book.getId()));
    }

    @Transactional(readOnly = true)
    public BaseResponse<BookRes.BookInfoRes> getBookInfo(Long id){
        if (!bookRepository.existsById(id)){
            return new BaseResponse<>(BaseResponseStatus.NOT_EXIST_BOOK_ID);
        }
        Book book = bookRepository.findById(id).orElseThrow();

        List<String> authorList = stringToList(book.getAuthor());
        return new BaseResponse<>(new BookRes.BookInfoRes(book.getName(),authorList, book.getPublisher(), book.getPublishedDate(),book.getPrice(),book.getIntroduction(),book.getSiteUrl(),book.getImgUrl()));
    }

    private String listToString(List<String> authorList){
        String author = "";
        for (String item : authorList) {
            author += item + ",";
        }
        return author;
    }

    private List<String> stringToList(String author){
        return Arrays.asList(author.split("\\s*,\\s*"));
    }
}