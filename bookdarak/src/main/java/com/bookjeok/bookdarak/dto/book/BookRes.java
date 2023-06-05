package com.bookjeok.bookdarak.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class BookRes {
    @Getter @AllArgsConstructor
    public static class FindBookRes{
        private Long id;
    }
}
