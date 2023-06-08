package com.bookjeok.bookdarak.controller;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.base.BaseResponseStatus;
import com.bookjeok.bookdarak.dto.book.BookReq;
import com.bookjeok.bookdarak.dto.book.BookRes;
import com.bookjeok.bookdarak.service.BookService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @ApiOperation(value = "도서 검색", notes = "책 isbn으로 책 검색해서 ID 반환; 책 없으면 DB에 저장 후 ID 반환")
    @PostMapping("/books")
    public BaseResponse<BookRes.BookFindRes> findBookByNameAndPub(@RequestBody BookReq.FindBookReq request){
        if (request.getName().isBlank()){
            return new BaseResponse<>(BaseResponseStatus.EMPTY_BOOK_NAME);
        }
        if (request.getAuthorList() == null || request.getAuthorList().isEmpty()){
            return new BaseResponse<>(BaseResponseStatus.EMPTY_BOOK_AUTHOR);
        }
        if (request.getIsbn().isBlank()){
            return new BaseResponse<>(BaseResponseStatus.EMPTY_BOOK_ISBN);
        }
        return bookService.findBookByName(request);
    }

    @ApiOperation(value = "책 정보 조회", notes = "책 아이디 사용")
    @GetMapping("/books/{id}")
    public BaseResponse<BookRes.BookInfoRes> getBookInfo(@PathVariable Long id){
        return bookService.getBookInfo(id);
    }
}
