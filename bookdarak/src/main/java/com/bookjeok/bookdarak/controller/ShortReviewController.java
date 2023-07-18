package com.bookjeok.bookdarak.controller;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.dto.shortReview.ShortReviewRes;
import com.bookjeok.bookdarak.service.ShortReviewService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ShortReviewController {
    private final ShortReviewService shortReviewService;

    @ApiOperation(value = "모든 공개 서평(요약) 조회", notes = "추천순, 최신순 정렬")
    @GetMapping("/reviews/shorts")
    public BaseResponse<List<ShortReviewRes>>
    getAllPublicReviews(@RequestParam(required = false, defaultValue = "likeCount") String orderCriteria) {
        return shortReviewService.getAllPublicReviews(orderCriteria);
    }

    @ApiOperation(value = "특정 책 공개 서평(요약) 조회", notes = "추천순, 최신순 정렬")
    @GetMapping("/reviews/shorts/books/{bookId}")
    public BaseResponse<List<ShortReviewRes>>
    getBookPublicReviews(@PathVariable Long bookId,
                         @RequestParam(required = false, defaultValue = "likeCount") String orderCriteria){
        return shortReviewService.getBookPublicReviews(bookId, orderCriteria);
    }


    @ApiOperation(value="유저 서평(요약) 조회", notes = "주인 여부 입력 필요")
    @GetMapping("/reviews/shorts/users/{userId}")
    public BaseResponse<List<ShortReviewRes>>
    getUserReviews(@PathVariable Long userId, @RequestParam String owner){
        return shortReviewService.getUserReviews(userId, owner);
    }

    @ApiOperation(value="요약 서평 추천")
    @PostMapping("/reviews/shorts/recommend/{userId}/{reviewId}")
    public BaseResponse<String> recommendShortReview(@PathVariable Long userId, @PathVariable Long reviewId)
    {
        return shortReviewService.recommendShortReview(userId, reviewId);
    }

    @ApiOperation(value="요약 서평 추천 취소")
    @DeleteMapping("/reviews/shorts/recommend/{userId}/{reviewId}")
    public BaseResponse<String> deleteShortReviewLike(@PathVariable Long userId, @PathVariable Long reviewId)
    {
        return shortReviewService.deleteShortReviewLike(userId, reviewId);
    }
}


