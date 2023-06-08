package com.bookjeok.bookdarak.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class BookReq {
    @Getter @AllArgsConstructor
    public static class FindBookReq{
        private String name;
        private List<String> authorList;
        private String isbn;
        private String imgUrl;
    }
}
