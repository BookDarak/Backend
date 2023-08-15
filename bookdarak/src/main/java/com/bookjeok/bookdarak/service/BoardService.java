package com.bookjeok.bookdarak.service;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.base.BaseResponseStatus;
import com.bookjeok.bookdarak.domain.Board;
import com.bookjeok.bookdarak.domain.Review;
import com.bookjeok.bookdarak.domain.ReviewLike;
import com.bookjeok.bookdarak.domain.User;
import com.bookjeok.bookdarak.dto.board.BoardReq;
import com.bookjeok.bookdarak.dto.board.BoardRes;
import com.bookjeok.bookdarak.dto.book.BookRes;
import com.bookjeok.bookdarak.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Optional;

import static com.bookjeok.bookdarak.base.BaseResponseStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    //게시판 등록
    @Transactional
    public BaseResponse<String> addBoard(BoardReq.boardInfo request)
    {
        boardRepository.save(new Board(request));
        return new BaseResponse<>("게시판을 등록하였습니다");
    }
    //게시판 삭제
    @Transactional
    public BaseResponse<String> deleteBoard(Long boardId){
        if (validateId(boardId)!=null){
            return validateId(boardId);
        }

        Board board = boardRepository.findById(boardId). orElse(null);
        boardRepository.delete(board);

        return new BaseResponse<>("게시판을 삭제했습니다.");

    }

    //게시판 조회
    @Transactional(readOnly = true)
    public BaseResponse<BoardRes.BoardInfo> getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId). orElse(null);

        if (board==null){
            return new BaseResponse<>(NOT_EXIST_BOARD_ID);
        }
        return new BaseResponse<>(new BoardRes.BoardInfo(board));
    }


    // 게시판 수정
    public BaseResponse<BaseResponseStatus> changeBoard(Long boardId, BoardReq.UpdateBoardInfo updateBoardInfo) {
        if (validateId(boardId)!=null){
            return new BaseResponse<>(NOT_EXIST_BOARD_ID);
        }
        Board board = boardRepository.findById(boardId). orElse(null);
        board.updateBoardInfo(updateBoardInfo);

        return new BaseResponse<>(UPDATE_SUCCESS);
    }

    public BaseResponse<String> validateId(Long boardId) {
        if (!boardRepository.existsById(boardId)) {
            return new BaseResponse<>(NOT_EXIST_BOARD_ID);
        }
        return null;
    }
}
