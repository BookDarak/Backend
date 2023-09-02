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
    public BaseResponse<String> addBoardComment(Long boardId, BoardCmntReq.boardcmntInfo request)
    {
        Board board = boardRepository.findById(boardId). orElse(null);
        User user = userRepository.findById(request.getUserId()).orElse(null);
        boardCmntRepository.save(new BoardCmnt(board,user,request.getContent()));
        return new BaseResponse<>("게시판 댓글을 등록하였습니다");
    }
    //게시판 댓글 삭제
    @Transactional
    public BaseResponse<String> deleteBoardComment(Long boardId, Long commentId){
        if (validateId(commentId)!=null){
            return validateId(commentId);
        }
        BoardCmnt boardCmnt = boardCmntRepository.findById(commentId).orElse(null);
        boardCmntRepository.delete(boardCmnt);
        return new BaseResponse<>("게시판 댓글을 삭제했습니다.");

    }

    //게시판 댓글 상세조회
    public BaseResponse<BoardCmntRes.BoardCmntInfo> getComment(Long boardId, Long commentId) {
        BoardCmnt boardCmnt = boardCmntRepository.findById(commentId). orElse(null);
        if(boardCmnt == null){
            return new BaseResponse<>(NOT_EXIST_BOARDCOMMENT_ID);
        }
        return new BaseResponse<>(new BoardCmntRes.BoardCmntInfo(boardCmnt));
    }

    //게시판 댓글 전체조회

    public BaseResponse<PageResponse<BoardCmntRes>> getBoardComment(Long boardId, Pageable pageable){
//        BoardCmnt boardCmnt = boardCmntRepository.findByBoardId(boardId). orElse(null);
//        if(boardCmnt == null){
//            return new BaseResponse<>(NOT_EXIST_BOARDCOMMENT_ID);
//        }
        Page<BoardCmnt> boardCmnts = boardCmntRepository.findByBoardId(boardId, pageable);
        PageResponse<BoardCmntRes> pageResponse = getShortReviewResPageResponse(boardCmnts);

        return new BaseResponse<>(pageResponse);
    }

    private static PageResponse<BoardCmntRes> getShortReviewResPageResponse(Page<BoardCmnt> boardCmnts) {
        Page<BoardCmntRes> shortReviewsPage = boardCmnts.map(BoardCmntRes::of);

        // Page->PageResponse
        return PageResponse.fromPage(shortReviewsPage);
    }



    public BaseResponse<String> validateId(Long commentId) {
        if (!boardCmntRepository.existsById(commentId)) {
            return new BaseResponse<>(NOT_EXIST_BOARDCOMMENT_ID);
        }
        return null;
    }
}
