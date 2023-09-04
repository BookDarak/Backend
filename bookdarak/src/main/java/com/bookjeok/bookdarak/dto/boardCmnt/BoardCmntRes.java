package com.bookjeok.bookdarak.dto.boardCmnt;

import com.bookjeok.bookdarak.domain.Board;
import com.bookjeok.bookdarak.domain.BoardCmnt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
@Getter
@AllArgsConstructor
@Builder
public class BoardCmntRes {
    private Long userId;
    private String content;
    private String userImg;

    public static BoardCmntRes of(BoardCmnt boardCmnt){
        return BoardCmntRes.builder()
                .userId(boardCmnt.getUser().getId())
                .content(boardCmnt.getContent())
                .userImg(boardCmnt.getUser().getProfileUrl())
                .build();
    }
}
