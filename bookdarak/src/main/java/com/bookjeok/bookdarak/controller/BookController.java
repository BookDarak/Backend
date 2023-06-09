package com.bookjeok.bookdarak.controller;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.dto.book.BookReq;
import com.bookjeok.bookdarak.dto.book.BookRes;
import com.bookjeok.bookdarak.service.BookService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
}
