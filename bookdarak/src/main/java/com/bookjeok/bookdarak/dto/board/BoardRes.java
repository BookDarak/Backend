package com.bookjeok.bookdarak.dto.board;

import com.bookjeok.bookdarak.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
@Getter
@AllArgsConstructor
@Builder
public class BoardRes {
    private Long bookId;
    private String question;
    private String bookname;
    private String bookImg;
    public static BoardRes of(Board board){
        return BoardRes.builder()
                .bookId(board.getBook().getId())
                .bookname(board.getBook().getName())
                .bookImg(board.getBook().getImgUrl())
                .question( board.getQuestion())
                .build();
    }

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
