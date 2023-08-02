package com.bookjeok.bookdarak.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class BookReq {
    @Getter @AllArgsConstructor @NoArgsConstructor
    public static class bookInfo {
        @NotBlank(message="도서명을 입력해주세요.")
        private String name;

        @NotNull(message="저자를 입력해주세요.")
        private List<String> authorList;

        @NotBlank(message="isbn을 입력해주세요.")
        private String isbn;

        private String imgUrl;
    }
}
