package com.bookjeok.bookdarak.controller;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.dto.shortReview.ShortReviewRes;
import com.bookjeok.bookdarak.service.ShortReviewService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
