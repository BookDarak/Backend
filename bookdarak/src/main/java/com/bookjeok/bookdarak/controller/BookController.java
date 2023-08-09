package com.bookjeok.bookdarak.controller;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.domain.Book;
import com.bookjeok.bookdarak.dto.book.BookReq;
import com.bookjeok.bookdarak.dto.book.BookRes;
import com.bookjeok.bookdarak.service.BookService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @ApiOperation(value = "도서 검색", notes = "책 isbn으로 책 검색해서 ID 반환; 책 없으면 DB에 저장 후 ID 반환")
    @PostMapping("/books")
    public BaseResponse<BookRes.BookId> findBookByNameAndPub(@Valid @RequestBody BookReq.bookInfo request){
        return bookService.findBookByIsbn(request);
    }

    @ApiOperation(value = "책 정보 조회", notes = "책 아이디 사용")
    @GetMapping("/books/{id}")
    public BaseResponse<BookRes.BookInfo> getBookInfo(@PathVariable Long id){
        return bookService.getBookInfo(id);
    }

    @ApiOperation(value = "연령대별 책 추천")
    @GetMapping("/books/recommend/age/{userId}")
    public BaseResponse<List<Book>> recommendBookByAge(@PathVariable Long userId){
        return bookService.recommendBookByAge(userId);
    }

    @ApiOperation(value = "성별 책 추천")
    @GetMapping("/books/recommend/gender/{userId}")
    public BaseResponse<List<Book>> recommendBookByGender(@PathVariable Long userId){
        return bookService.recommendBookByGender(userId);
    }
}
