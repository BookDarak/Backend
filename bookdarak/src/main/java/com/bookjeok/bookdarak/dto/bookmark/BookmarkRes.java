package com.bookjeok.bookdarak.dto.bookmark;

import com.bookjeok.bookdarak.domain.Book;
import com.bookjeok.bookdarak.domain.Bookmark;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter @AllArgsConstructor
public class BookmarkRes {
    public Long bookId;
    public String name;
    public String bookImgUrl;

    public static List<BookmarkRes> getBookmarkResList(List<Bookmark> bookmarks){
        List<BookmarkRes> bookmarkResList = new ArrayList<>();
        for (Bookmark bookmark: bookmarks) {
            Book book = bookmark.getBook();
            bookmarkResList.add(new BookmarkRes(book.getId(), book.getName(), book.getImgUrl()));
        }
        return bookmarkResList;
    }
}


