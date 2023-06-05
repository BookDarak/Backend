package com.bookjeok.bookdarak.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

public class BookReq {
    @Getter @AllArgsConstructor
    public static class FindBookReq{
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
