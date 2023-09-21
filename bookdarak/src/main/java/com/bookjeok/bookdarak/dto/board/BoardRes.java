package com.bookjeok.bookdarak.dto.board;

import com.bookjeok.bookdarak.domain.Board;
import com.bookjeok.bookdarak.domain.BoardCmnt;
import com.bookjeok.bookdarak.domain.Book;
import com.bookjeok.bookdarak.dto.boardCmnt.BoardCmntRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

import static org.hibernate.boot.model.process.spi.MetadataBuildingProcess.build;
@Getter
@AllArgsConstructor
@Builder
public class BoardRes {
    private Long boardId;
    private Long bookId;
    private String question;
    private String bookname;
    private String bookImg;
    public static BoardRes of(Board board){
        return BoardRes.builder()
                .boardId(board.getId())
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
