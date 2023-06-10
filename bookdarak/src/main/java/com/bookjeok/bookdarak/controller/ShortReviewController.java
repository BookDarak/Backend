package com.bookjeok.bookdarak.controller;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.dto.shortReview.ShortReviewRes;
import com.bookjeok.bookdarak.service.ShortReviewService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ShortReviewController {
    private final ShortReviewService shortReviewService;

    @ApiOperation(value = "모든 공개 서평(요약) 조회", notes = "추천순, 최신순 정렬")
    @GetMapping("/reviews/short")
    public BaseResponse<List<ShortReviewRes.publicReviews>>
    getPublicReviews(@RequestParam(required = false, defaultValue = "likeCount") String orderCriteria) {
        return shortReviewService.getPublicReviews(orderCriteria);
    }
}
