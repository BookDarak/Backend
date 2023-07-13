package com.bookjeok.bookdarak.base;


import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler
    protected BaseResponse<BaseResponseStatus> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        return extractErrors(errors);
    }

    private BaseResponse<BaseResponseStatus> extractErrors(List<String> errors){
        // 회원
        BaseResponseStatus email = BaseResponseStatus.EMPTY_USER_EMAIL;
        BaseResponseStatus emailFormat = BaseResponseStatus.INVALID_USER_EMAIL;
        BaseResponseStatus pwd = BaseResponseStatus.EMPTY_USER_PASSWORD;
        BaseResponseStatus pwdFormat = BaseResponseStatus.INVALID_FORMAT_PASSWORD;
        BaseResponseStatus age = BaseResponseStatus.EMPTY_USER_AGE;
        BaseResponseStatus name = BaseResponseStatus.EMPTY_USER_NICKNAME;
        BaseResponseStatus intro = BaseResponseStatus.EMPTY_USER_INTRO;


        // 도서
        BaseResponseStatus bookName = BaseResponseStatus.EMPTY_BOOK_NAME;
        BaseResponseStatus isbn = BaseResponseStatus.EMPTY_BOOK_ISBN;
        BaseResponseStatus authors = BaseResponseStatus.EMPTY_BOOK_AUTHOR;

        //리뷰
        BaseResponseStatus rating = BaseResponseStatus.EMPTY_REVIEW_RATING;
        BaseResponseStatus content = BaseResponseStatus.EMPTY_REVIEW_CONTENT;
        BaseResponseStatus publicYn = BaseResponseStatus.EMPTY_PUBLIC_YN;
        BaseResponseStatus sDate = BaseResponseStatus.EMPTY_REVIEW_START_DATE;
        BaseResponseStatus eDate = BaseResponseStatus.EMPTY_REVIEW_END_DATE;

        //캘린더
        BaseResponseStatus calStart = BaseResponseStatus.CALENDAR_EMPTY_START;
        BaseResponseStatus calEnd = BaseResponseStatus.CALENDAR_EMPTY_END;

        BaseResponseStatus[] statusList = new BaseResponseStatus[]{email, emailFormat, pwd, pwdFormat, name, age, intro,
                bookName, authors, isbn,
                rating, content, publicYn, sDate, eDate,
                calStart, calEnd};

        for (BaseResponseStatus status: statusList) {
            if (errors.contains(status.getMessage())){
                return new BaseResponse<>(status);
            }
        }
        return new BaseResponse<>(BaseResponseStatus.RESPONSE_ERROR);

    }


}