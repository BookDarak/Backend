package com.bookjeok.bookdarak.dto.board;

import com.bookjeok.bookdarak.domain.Book;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BoardReq {
    @Getter @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class boardInfo {
        @NotNull
        private Book book;
        @NotBlank
        private String question;
    }

    @Getter @AllArgsConstructor
    public static class UpdateBoardInfo {
        private Book book;
        private String question;
    }
}
