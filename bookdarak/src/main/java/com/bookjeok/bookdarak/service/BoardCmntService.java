package com.bookjeok.bookdarak.service;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.base.PageResponse;
import com.bookjeok.bookdarak.domain.*;
import com.bookjeok.bookdarak.dto.board.BoardReq;
import com.bookjeok.bookdarak.dto.boardCmnt.BoardCmntReq;
import com.bookjeok.bookdarak.dto.boardCmnt.BoardCmntRes;
import com.bookjeok.bookdarak.dto.shortReview.ShortReviewRes;
import com.bookjeok.bookdarak.repository.BoardCmntRepository;
import com.bookjeok.bookdarak.repository.BoardRepository;
import com.bookjeok.bookdarak.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

import static com.bookjeok.bookdarak.base.BaseResponseStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardCmntService {
    private final BoardCmntRepository boardCmntRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    //게시판 댓글 등록
    @Transactional
    public BaseResponse<String> addBoardComment(Long boardId, Long userId, BoardCmntReq.boardcmntInfo request)
    {
        Board board = boardRepository.findById(boardId). orElse(null);
        if (board == null){
            return new BaseResponse<>(NOT_EXIST_BOARD_ID);
        }

        User user = userRepository.findById(userId).orElse(null);
        if (user == null){
            return new BaseResponse<>(NOT_EXIST_USER_ID);
        }

        boardCmntRepository.save(new BoardCmnt(board,user,request.getContent()));
        return new BaseResponse<>("게시판 댓글을 등록하였습니다");
    }
    //게시판 댓글 삭제
    @Transactional
    public BaseResponse<String> deleteBoardComment(Long commentId){
        BoardCmnt boardCmnt = boardCmntRepository.findById(commentId).orElse(null);
        if (boardCmnt== null){
            return new BaseResponse<>(NOT_EXIST_BOARDCOMMENT_ID);
        }

        boardCmntRepository.delete(boardCmnt);
        return new BaseResponse<>("게시판 댓글을 삭제했습니다.");

    }

    //게시판 댓글 전체조회
    public BaseResponse<PageResponse<BoardCmntRes>> getBoardComment(Long boardId, Pageable pageable){
        Board board = boardRepository.findById(boardId). orElse(null);
        if (board == null){
            return new BaseResponse<>(NOT_EXIST_BOARD_ID);
        }

        Page<BoardCmnt> boardCmnts = boardCmntRepository.findByBoardId(boardId, pageable);
        PageResponse<BoardCmntRes> pageResponse = getShortReviewResPageResponse(boardCmnts);

        return new BaseResponse<>(pageResponse);
    }
    private static PageResponse<BoardCmntRes> getShortReviewResPageResponse(Page<BoardCmnt> boardCmnts) {
        Page<BoardCmntRes> shortReviewsPage = boardCmnts.map(BoardCmntRes::of);
        // Page->PageResponse
        return PageResponse.fromPage(shortReviewsPage);
    }

}
