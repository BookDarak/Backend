package com.bookjeok.bookdarak.controller;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.base.BaseResponseStatus;
import com.bookjeok.bookdarak.base.PageResponse;
import com.bookjeok.bookdarak.dto.boardCmnt.BoardCmntReq;
import com.bookjeok.bookdarak.dto.boardCmnt.BoardCmntRes;
import com.bookjeok.bookdarak.dto.shortReview.ShortReviewRes;
import com.bookjeok.bookdarak.dto.user.UserReq;
import com.bookjeok.bookdarak.service.BoardCmntService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @ApiOperation(value="게시판 댓글 상세 조회")
    @GetMapping("/boards/{boardId}/{commentId}")
    public BaseResponse<BoardCmntRes.BoardCmntInfo> getComment(@PathVariable Long boardId, @PathVariable Long commentId)
    {
        return boardCmntService.getComment(boardId,commentId);
    }

    @ApiOperation(value="게시판 댓글 전체조회", notes = "")
    @GetMapping("/boards/comment/{boardId}")
    public BaseResponse<PageResponse<BoardCmntRes>>
    getBoardComment(@PathVariable Long boardId, @PageableDefault(sort="createdAt",direction = Sort.Direction.DESC) Pageable pageable){
        return boardCmntService.getBoardComment(boardId,pageable);
    }

//    @ApiOperation(value="유저 서평(요약) 조회", notes = "주인 여부 입력 필요")
//    @GetMapping("/reviews/shorts/users/{userId}")
//    public BaseResponse<PageResponse<ShortReviewRes>>
//    getUserReviews(@PathVariable Long userId, @RequestParam String isOwner,
//                   @PageableDefault(sort="createdAt",direction = Sort.Direction.DESC) Pageable pageable){
//        return shortReviewService.getUserReviews(userId, isOwner,pageable);
//    }


}
