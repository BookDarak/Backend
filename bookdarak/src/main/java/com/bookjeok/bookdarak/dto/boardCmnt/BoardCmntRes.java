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
    private Long boardId;

    public static BoardCmntRes of(BoardCmnt boardCmnt){
        return BoardCmntRes.builder()
                .boardId(boardCmnt.getBoard().getId())
                .userId(boardCmnt.getUser().getId())
                .content(boardCmnt.getContent())
                .userImg(boardCmnt.getUser().getProfileUrl())
                .build();
    }
    @Getter
    @AllArgsConstructor
    public static class BoardCmntInfo {
        private Long userId;
        private String content;
        private String userImg;
        private Long boardId;

        public BoardCmntInfo(BoardCmnt boardCmnt){
            this.boardId = boardCmnt.getBoard().getId();
            this.userId = boardCmnt.getUser().getId();
            this.content = boardCmnt.getContent();
            this.userImg = boardCmnt.getUser().getProfileUrl();
        }
    }
}
