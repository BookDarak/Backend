package com.bookjeok.bookdarak.controller;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.dto.bookmark.BookmarkRes;
import com.bookjeok.bookdarak.service.BookmarkService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @ApiOperation(value="북마크 추가")
    @PostMapping("/bookmarks/{userId}/{bookId}")
    public BaseResponse<String> addBookmark(@PathVariable Long userId, @PathVariable Long bookId){
        return bookmarkService.addBookmark(userId, bookId);
    }

    @ApiOperation(value="북마크 삭제")
    @DeleteMapping("/bookmarks/{userId}/{bookId}")
    public BaseResponse<String> deleteBookmark(@PathVariable Long userId, @PathVariable Long bookId){
        return bookmarkService.deleteBookmark(userId, bookId);
    }

    @ApiOperation(value="북마크 여부 조회")
    @GetMapping("/bookmarks/{userId}/{bookId}")
    public BaseResponse<String> getBookmarkStatus(@PathVariable Long userId, @PathVariable Long bookId){
        return bookmarkService.getBookmarkStatus(userId, bookId);
    }

    @ApiOperation(value="유저의 모든 북마크 조회")
    @GetMapping("/bookmarks/{userId}")
    public BaseResponse<List<BookmarkRes>> getUserBookmarks(@PathVariable Long userId){
        return bookmarkService.getUserBookmarks(userId);
    }

}
