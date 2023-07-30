package com.bookjeok.bookdarak.dto.review;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

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
        private LocalDate startDate;
        private LocalDate endDate;
    }

    @Getter
    public static class Calendar{
        @NotNull(message = "캘린더 시작 날짜를 입력해주세요.")
        private LocalDate calStartDate;
        @NotNull(message = "캘린더 종료 날짜를 입력해주세요.")
        private LocalDate calEndDate;
    }

    //시작일, 종료일 크기 비교
    public static boolean isInValidDateInterval(LocalDate stDate, LocalDate edDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate;
        Date endDate;

        try {
            startDate = dateFormat.parse( String.valueOf(stDate) );
            endDate = dateFormat.parse( String.valueOf(edDate) );
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        int compare = startDate.compareTo( endDate );

        return (compare > 0);
    }

    public static boolean isInValidPublicYn(String publicYn){
        return !(publicYn.equals("Y") || publicYn.equals("N"));
    }
}
