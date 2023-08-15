package com.bookjeok.bookdarak.domain;

import com.bookjeok.bookdarak.base.BaseEntity;
import com.bookjeok.bookdarak.dto.board.BoardReq;
import com.bookjeok.bookdarak.dto.boardCmnt.BoardCmntReq;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardCmnt extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "board_cmnt_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String content;


    public BoardCmnt(BoardCmntReq.boardcmntInfo dto) {
        this.board = dto.getBoard();
        this.user= dto.getUser();
        this.content = dto.getContent();
    }
}
