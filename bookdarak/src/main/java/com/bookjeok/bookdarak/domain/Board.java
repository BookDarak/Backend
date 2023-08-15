package com.bookjeok.bookdarak.domain;

import com.bookjeok.bookdarak.base.BaseEntity;
import com.bookjeok.bookdarak.dto.board.BoardReq;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private String question;

    public Board(BoardReq.boardInfo dto){
        this.book = dto.getBook();
        this.question = dto.getQuestion();
    }

    public void updateBoardInfo(BoardReq.UpdateBoardInfo boardInfo) {
        if (boardInfo.getBook()!=null){
            this.book = boardInfo.getBook();
        }
            if (boardInfo.getQuestion()!=null){
            this.question = boardInfo.getQuestion();
        }
    }
}
