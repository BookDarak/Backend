package com.bookjeok.bookdarak.dto.review;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ReviewReq {
    @Getter
    public static class AddReviewReq{
        @NotNull(message="평점을 입력해주세요.")
        private BigDecimal rating;

        @NotBlank(message="서평 내용을 입력해주세요.")
        private String content;

        private String phrase;

        @NotBlank(message="공개여부를 입력해주세요.")
        private String publicYn;

        private int likeCount;

        @NotNull(message="시작 독서일을 선택해주세요.")
        private LocalDate startDate;

        @NotNull(message="완료 독서일을 선택해주세요.")
        private LocalDate endDate;
    }

    @Getter
    public static class UpdateReviewReq{
        private BigDecimal rating;
        private String content;
        private String phrase;
        private String publicYn;
        private int likeCount;
        private LocalDate startDate;
        private LocalDate endDate;
    }
}
