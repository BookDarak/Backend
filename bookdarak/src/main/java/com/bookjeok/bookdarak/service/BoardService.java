package com.bookjeok.bookdarak.service;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.base.BaseResponseStatus;
import com.bookjeok.bookdarak.domain.*;
import com.bookjeok.bookdarak.dto.board.BoardReq;
import com.bookjeok.bookdarak.dto.board.BoardRes;
import com.bookjeok.bookdarak.dto.book.BookRes;
import com.bookjeok.bookdarak.repository.BoardCmntRepository;
import com.bookjeok.bookdarak.repository.BoardRepository;
import com.bookjeok.bookdarak.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardCmntRepository boardCmntRepository;
    private final BookRepository bookRepository;

    //게시판 등록
    @Transactional
    public BaseResponse<String> addBoard(BoardReq.boardInfo request)
    {
        Book book = bookRepository.findById(request.getBookId()). orElse(null);
        if (book == null){
            return new BaseResponse<>(NOT_EXIST_BOOK_ID);
        }
        Board board =  boardRepository.save(new Board(book,request.getQuestion()));
        String BoardId = Integer.toString(Math.toIntExact(board.getId()));
        return new BaseResponse<>(BoardId);
    }
    //게시판 삭제
    @Transactional
    public BaseResponse<String> deleteBoard(Long boardId){
        if (validateId(boardId)!=null){
            return validateId(boardId);
        }

        Board board = boardRepository.findById(boardId). orElse(null);
        boardRepository.delete(board);
        boardCmntRepository.deleteBoardCmntByBoard(board);

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

        Book book = bookRepository.findById(updateBoardInfo.getBookId()). orElse(null);
        if (book == null){
            return new BaseResponse<>(NOT_EXIST_BOOK_ID);
        }

        Board board = boardRepository.findById(boardId). orElse(null);
        board.updateBoardInfo(book, updateBoardInfo.getQuestion());

        return new BaseResponse<>(UPDATE_SUCCESS);
    }

    public BaseResponse<String> validateId(Long boardId) {
        if (!boardRepository.existsById(boardId)) {
            return new BaseResponse<>(NOT_EXIST_BOARD_ID);
        }
        return null;
    }
}
