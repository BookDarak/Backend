package com.bookjeok.bookdarak.controller;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.base.BaseResponseStatus;
import com.bookjeok.bookdarak.dto.book.BookReq;
import com.bookjeok.bookdarak.dto.book.BookRes;
import com.bookjeok.bookdarak.service.BookService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @ApiOperation(value = "도서 검색", notes = "책 이름과 출판사로 책 검색해서 ID 반환; 책 없으면 DB에 저장 후 ID 반환")
    @PostMapping("/book")
    public BaseResponse<BookRes.FindBookRes> findBookByNameAndPub(@RequestBody BookReq.FindBookReq request){
        if (request.getName().isBlank()){
            return new BaseResponse<>(BaseResponseStatus.EMPTY_BOOK_NAME);
        }
        if (request.getAuthor().isBlank()){
            return new BaseResponse<>(BaseResponseStatus.EMPTY_BOOK_AUTHOR);
        }
        if (request.getPublisher().isBlank()){
            return new BaseResponse<>(BaseResponseStatus.EMPTY_BOOK_PUBLISHER);
        }
        return bookService.findBookByName(request);
    }
}