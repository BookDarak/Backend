package com.bookjeok.bookdarak.dto.boardCmnt;

import com.bookjeok.bookdarak.domain.Board;
import com.bookjeok.bookdarak.domain.Book;
import com.bookjeok.bookdarak.domain.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BoardCmntReq {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class boardcmntInfo {
        @NotBlank
        private String content;
    }
}
