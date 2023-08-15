package com.bookjeok.bookdarak.controller;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.base.BaseResponseStatus;
import com.bookjeok.bookdarak.dto.boardCmnt.BoardCmntReq;
import com.bookjeok.bookdarak.dto.boardCmnt.BoardCmntRes;
import com.bookjeok.bookdarak.dto.user.UserReq;
import com.bookjeok.bookdarak.service.BoardCmntService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class BoardCmntController {
    private final BoardCmntService boardCmntService;

    @ApiOperation(value="게시판 댓글 등록")
    @PostMapping("/boards/{boardId}")
    public BaseResponse<String> addBoardComment(@PathVariable Long boardId, @Valid @RequestBody BoardCmntReq.boardcmntInfo request)
    {
        return boardCmntService.addBoardComment(boardId, request);
    }

    @ApiOperation(value="게시판 댓글 삭제")
    @DeleteMapping("/boards/{boardId}/{commentId}")
    public BaseResponse<String> deleteBoardComment(@PathVariable Long boardId,@PathVariable Long commentId)
    {
        return boardCmntService.deleteBoardComment(boardId,commentId);
    }

    @ApiOperation(value="게시판 댓글 조회")
    @GetMapping("/boards/{boardId}/{commentId}")
    public BaseResponse<BoardCmntRes.BoardCmntInfo> getBoardComment(@PathVariable Long boardId, @PathVariable Long commentId)
    {
        return boardCmntService.getBoardComment(boardId,commentId);
    }


}
