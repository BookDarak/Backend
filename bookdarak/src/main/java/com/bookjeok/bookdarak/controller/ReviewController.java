package com.bookjeok.bookdarak.controller;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.base.BaseResponseStatus;
import com.bookjeok.bookdarak.dto.review.ReviewReq;
import com.bookjeok.bookdarak.dto.review.ReviewRes;
import com.bookjeok.bookdarak.service.ReviewService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @ApiOperation(value = "서평 작성")
    @PostMapping("/reviews/{userId}/{bookId}")
    public BaseResponse<ReviewRes.ReviewId> addReview (@PathVariable Long userId, @PathVariable Long bookId, @Valid @RequestBody ReviewReq.AddReviewReq request){
        if (ReviewReq.isInValidPublicYn(request.getPublicYn())){
            return new BaseResponse<>(BaseResponseStatus.WRONG_REVIEW_PUBLIC_FORMAT);

        }
        if (ReviewReq.isInValidDateInterval(request.getStartDate(),request.getEndDate())){
            return new BaseResponse<>(BaseResponseStatus.INVALID_DATE_INTERVAL);
        }
        return reviewService.addReview(userId, bookId, request);
    }

    @ApiOperation(value = "서평 수정")
    @PatchMapping("/reviews/{reviewId}")
    public BaseResponse<BaseResponseStatus> updateReview(@RequestBody ReviewReq.UpdateReviewReq request,@PathVariable Long reviewId ){
        if (request.getContent()!=null && request.getContent().isBlank()){
            return new BaseResponse<>(BaseResponseStatus.EMPTY_REVIEW_CONTENT);
        }
        if (request.getPublicYn()!=null && ReviewReq.isInValidPublicYn(request.getPublicYn())){
            return new BaseResponse<>(BaseResponseStatus.WRONG_REVIEW_PUBLIC_FORMAT);
        }
        if (request.getStartDate()!=null&& request.getEndDate()!=null&& ReviewReq.isInValidDateInterval(request.getStartDate(),request.getEndDate())){
            return new BaseResponse<>(BaseResponseStatus.INVALID_DATE_INTERVAL);
        }
        return reviewService.updateReview(request, reviewId);
    }

    @ApiOperation(value = "서평 조회")
    @GetMapping("/reviews/{reviewId}")
    public BaseResponse<ReviewRes.GetReviewRes> getReview(@PathVariable Long reviewId ){
        return reviewService.getReview(reviewId);
    }

    @ApiOperation(value = "서평 Id 조회", notes = "해당 서평 없으면 id는 -1 반환")
    @GetMapping("/reviews/{userId}/{bookId}")
    public BaseResponse<ReviewRes.ReviewId> getReviewId(@PathVariable Long userId, @PathVariable Long bookId){
        return reviewService.getReviewExistence(userId, bookId);
    }

    @ApiOperation(value = "서평 삭제")
    @DeleteMapping("/reviews/{reviewId}")
    public BaseResponse<String> deleteReview(@PathVariable Long reviewId){
        return reviewService.deleteReview(reviewId);
    }
}
