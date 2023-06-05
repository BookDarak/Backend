package com.bookjeok.bookdarak.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

public class BookRes {
    @Getter @AllArgsConstructor
    public static class BookFindRes {
        private Long id;
    }

    @Getter @AllArgsConstructor
    public static class BookInfoRes{
        private String name;
        private String author;
        private String publisher;
        private LocalDate publishedDate;
        private int price;
        private String introduction;
        private String siteUrl;
        private String imgUrl;
    }
}
