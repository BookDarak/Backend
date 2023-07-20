package com.bookjeok.bookdarak.dto.book;


import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class BookmarkRes {
    public Long bookId;
    public String name;
    public String bookImgUrl;
}


