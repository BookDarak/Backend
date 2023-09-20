package com.bookjeok.bookdarak.dto.board;

import com.bookjeok.bookdarak.domain.Board;
import com.bookjeok.bookdarak.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class BoardRes {

    @Getter
    @AllArgsConstructor
    public static class BoardInfo {
        private Long bookId;
        private String question;
        private String bookname;
        private String bookImg;

        public BoardInfo(Board board){
            this.bookId = board.getBook().getId();
            this.bookname=board.getBook().getName();
            this.bookImg = board.getBook().getImgUrl();
            this.question = board.getQuestion();
        }
    }
}
