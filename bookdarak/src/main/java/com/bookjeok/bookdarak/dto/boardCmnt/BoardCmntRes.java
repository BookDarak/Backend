package com.bookjeok.bookdarak.dto.boardCmnt;

import com.bookjeok.bookdarak.domain.Board;
import com.bookjeok.bookdarak.domain.BoardCmnt;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class BoardCmntRes {
    @Getter
    @AllArgsConstructor
    public static class BoardCmntInfo {
        private Long userId;
        private String content;
        private String userImg;

        public BoardCmntInfo(BoardCmnt boardCmnt){
            this.userId = boardCmnt.getUser().getId();
            this.content = boardCmnt.getContent();
            this.userImg = boardCmnt.getUser().getProfileUrl();
        }
    }
}
