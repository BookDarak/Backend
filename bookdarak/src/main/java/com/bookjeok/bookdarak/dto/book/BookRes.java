package com.bookjeok.bookdarak.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class BookRes {
    @Getter @AllArgsConstructor
    public static class BookFindRes {
        private Long id;
    }

    @Getter @AllArgsConstructor
    public static class BookInfoRes{
        private String name;
        private List<String> authorList;
        private String publisher;
        private LocalDate publishedDate;
        private int price;
        private String introduction;
        private String siteUrl;
        private String imgUrl;
    }
}
