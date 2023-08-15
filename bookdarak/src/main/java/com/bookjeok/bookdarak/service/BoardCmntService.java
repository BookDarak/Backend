package com.bookjeok.bookdarak.service;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.domain.Board;
import com.bookjeok.bookdarak.domain.BoardCmnt;
import com.bookjeok.bookdarak.dto.board.BoardReq;
import com.bookjeok.bookdarak.dto.boardCmnt.BoardCmntReq;
import com.bookjeok.bookdarak.dto.boardCmnt.BoardCmntRes;
import com.bookjeok.bookdarak.repository.BoardCmntRepository;
import com.bookjeok.bookdarak.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

import static com.bookjeok.bookdarak.base.BaseResponseStatus.NOT_EXIST_BOARDCOMMENT_ID;
import static com.bookjeok.bookdarak.base.BaseResponseStatus.NOT_EXIST_BOARD_ID;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardCmntService {
    private final BoardCmntRepository boardCmntRepository;
    private final BoardRepository boardRepository;

    //게시판 댓글 등록
    @Transactional
    public BaseResponse<String> addBoardComment(Long boardId, BoardCmntReq.boardcmntInfo request)
    {
        request.setBoard(boardRepository.findById(boardId). orElse(null));
        boardCmntRepository.save(new BoardCmnt(request));
        return new BaseResponse<>("게시판 댓글을 등록하였습니다");
    }
    //게시판 댓글 삭제
    @Transactional
    public BaseResponse<String> deleteBoardComment(Long boardId, Long commentId){
        if (validateId(commentId)!=null){
            return validateId(boardId);
        }

        BoardCmnt boardCmnt = boardCmntRepository.findById(commentId). orElse(null);
        boardCmntRepository.delete(boardCmnt);
        return new BaseResponse<>("게시판 댓글을 삭제했습니다.");

    }

    //게시판 댓글 조회
    public BaseResponse<BoardCmntRes.BoardCmntInfo> getBoardComment(Long boardId, Long commentId) {
        BoardCmnt boardCmnt = boardCmntRepository.findById(commentId). orElse(null);

        if(boardCmnt == null){
            return new BaseResponse<>(NOT_EXIST_BOARDCOMMENT_ID);
        }

        return new BaseResponse<>(new BoardCmntRes.BoardCmntInfo(boardCmnt));
    }

    public BaseResponse<String> validateId(Long commentId) {
        if (!boardCmntRepository.existsById(commentId)) {
            return new BaseResponse<>(NOT_EXIST_BOARDCOMMENT_ID);
        }
        return null;
    }
}
