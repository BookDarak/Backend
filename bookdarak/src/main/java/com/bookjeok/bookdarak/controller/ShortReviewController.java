package com.bookjeok.bookdarak.controller;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.base.PageResponse;
import com.bookjeok.bookdarak.dto.shortReview.ShortReviewRes;
import com.bookjeok.bookdarak.service.ShortReviewService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ShortReviewController {
    private final ShortReviewService shortReviewService;

    @ApiOperation(value = "모든 공개 서평(요약) 조회", notes = "추천순, 최신순 정렬")
    @GetMapping("/reviews/shorts")
    public BaseResponse<PageResponse<ShortReviewRes>>
    getAllPublicReviews(@RequestParam(required = false, defaultValue = "likeCount") String orderCriteria,
                        @RequestParam(required = false, defaultValue = "0") int pageNo,
                        @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return shortReviewService.getAllPublicReviews(orderCriteria, pageNo, pageSize);
    }

    @ApiOperation(value = "특정 책 공개 서평(요약) 조회", notes = "추천순, 최신순 정렬")
    @GetMapping("/reviews/shorts/books/{bookId}")
    public BaseResponse<PageResponse<ShortReviewRes>>
    getBookPublicReviews(@PathVariable Long bookId,
                         @RequestParam(required = false, defaultValue = "likeCount") String orderCriteria,
                        @RequestParam(required = false, defaultValue = "0") int pageNo,
                        @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return shortReviewService.getBookPublicReviews(bookId, orderCriteria, pageNo, pageSize);
    }


    @ApiOperation(value="유저 서평(요약) 조회", notes = "주인 여부 입력 필요")
    @GetMapping("/reviews/shorts/users/{userId}")
    public BaseResponse<PageResponse<ShortReviewRes>>
    getUserReviews(@PathVariable Long userId, @RequestParam String isOwner,
                   @RequestParam(required = false, defaultValue = "0") int pageNo,
                   @RequestParam(required = false, defaultValue = "10") int pageSize){
        return shortReviewService.getUserReviews(userId, isOwner, pageNo, pageSize);
    }

    @ApiOperation(value="요약 서평 추천")
    @PostMapping("/reviews/shorts/recommend/{userId}/{reviewId}")
    public BaseResponse<String> likeShortReview(@PathVariable Long userId, @PathVariable Long reviewId)
    {
        return shortReviewService.likeShortReview(userId, reviewId);
    }

    @ApiOperation(value="요약 서평 추천 취소")
    @DeleteMapping("/reviews/shorts/recommend/{userId}/{reviewId}")
    public BaseResponse<String> deleteShortReviewLike(@PathVariable Long userId, @PathVariable Long reviewId)
    {
        return shortReviewService.deleteShortReviewLike(userId, reviewId);
    }

    @ApiOperation(value="요약 서평 추천 여부 조회")
    @GetMapping("/reviews/shorts/recommend/{userId}/{reviewId}")
    public BaseResponse<String> getShortReviewLikeStatus(@PathVariable Long userId, @PathVariable Long reviewId)
    {
        return shortReviewService.getShortReviewLikeStatus(userId, reviewId);
    }

    @ApiOperation(value="요약 서평 추천 수 조회")
    @GetMapping("/reviews/shorts/recommend/{reviewId}")
    public BaseResponse<String> getShortReviewLikeCount(@PathVariable Long reviewId)
    {
        return shortReviewService.getShortReviewLikeCount(reviewId);
    }
}