package com.bookjeok.bookdarak.controller;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.base.BaseResponseStatus;
import com.bookjeok.bookdarak.dto.review.ReviewReq;
import com.bookjeok.bookdarak.dto.review.ReviewRes;
import com.bookjeok.bookdarak.service.ReviewService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @ApiOperation(value = "서평 작성")
    @PostMapping("/reviews/{userId}/{bookId}")
    public BaseResponse<ReviewRes.AddReviewRes> addReview (@PathVariable Long userId, @PathVariable Long bookId, @RequestBody ReviewReq.AddReviewReq request){
        if (request.getRating()==null){
            return new BaseResponse<>(BaseResponseStatus.EMPTY_REVIEW_RATING);
        }
        if (request.getContent().isBlank()){
            return new BaseResponse<>(BaseResponseStatus.EMPTY_REVIEW_CONTENT);

        }
        if (!reviewService.isValidPublicYn(request.getPublicYn())){
            return new BaseResponse<>(BaseResponseStatus.WRONG_REVIEW_PUBLIC_FORMAT);

        }

        if (request.getStartDate()==null){
            return new BaseResponse<>(BaseResponseStatus.EMPTY_REVIEW_START_DATE);

        }
        if (request.getEndDate()==null){
            return new BaseResponse<>(BaseResponseStatus.EMPTY_REVIEW_END_DATE);

        }
        if (reviewService.isValidDateInterval(request.getStartDate(),request.getEndDate())){
            return new BaseResponse<>(BaseResponseStatus.INVALID_DATE_INTERVAL);
        }
        return reviewService.addReview(userId, bookId, request);
    }

    @ApiOperation(value = "유저Id와 책Id로 서평 조회")
    @GetMapping("/reviews/{userId}/{bookId}")
    public BaseResponse<ReviewRes.GetReviewRes> getReview(@PathVariable Long userId, @PathVariable Long bookId ){
        return reviewService.getReview(userId, bookId);
    }
}
