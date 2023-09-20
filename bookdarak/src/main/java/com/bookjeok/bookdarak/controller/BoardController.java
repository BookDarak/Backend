package com.bookjeok.bookdarak.controller;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.base.BaseResponseStatus;
import com.bookjeok.bookdarak.base.PageResponse;
import com.bookjeok.bookdarak.domain.Board;
import com.bookjeok.bookdarak.dto.board.BoardReq;
import com.bookjeok.bookdarak.dto.board.BoardRes;
import com.bookjeok.bookdarak.dto.boardCmnt.BoardCmntRes;
import com.bookjeok.bookdarak.dto.book.BookReq;
import com.bookjeok.bookdarak.dto.user.UserReq;
import com.bookjeok.bookdarak.service.BoardService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final BoardService boardService;
    @ApiOperation(value="게시판 등록")
    @PostMapping("/boards")
    public BaseResponse<String> addBoard(@Valid @RequestBody BoardReq.boardInfo request){
        return boardService.addBoard(request);
    }

    @ApiOperation(value="게시판 삭제")
    @DeleteMapping("/boards/{boardId}")
    public BaseResponse<String> deleteBoard(@PathVariable Long boardId)
    {
        return boardService.deleteBoard(boardId);
    }

    @ApiOperation(value="게시판 조회")
    @GetMapping("/boards/{boardId}")
    public BaseResponse<BoardRes.BoardInfo> getBoard(@PathVariable Long boardId)
    {
        return boardService.getBoard(boardId);
    }

    @ApiOperation(value = "게시판 수정")
    @PatchMapping("/boards/{boardId}")
    public BaseResponse<BaseResponseStatus> changeBoard(@PathVariable Long boardId, @RequestBody BoardReq.UpdateBoardInfo updateBoardInfo){
        return boardService.changeBoard(boardId, updateBoardInfo);}

    @ApiOperation(value="게시판 전체조회")
    @GetMapping("/boards")
    public BaseResponse<PageResponse<BoardRes>>
    getAllBoard(@PageableDefault(sort="createdAt",direction = Sort.Direction.DESC) Pageable pageable){
        return boardService.getAllBoard(pageable);
    }

}
