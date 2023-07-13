package com.bookjeok.bookdarak.base;


import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

import static com.bookjeok.bookdarak.base.BaseResponseStatus.*;

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
        BaseResponseStatus[] statusList = new BaseResponseStatus[]{
                EMPTY_USER_EMAIL, INVALID_USER_EMAIL, EMPTY_USER_PASSWORD, INVALID_FORMAT_PASSWORD, EMPTY_USER_AGE, EMPTY_USER_NICKNAME, EMPTY_USER_INTRO,
                EMPTY_BOOK_NAME, EMPTY_BOOK_ISBN, EMPTY_BOOK_AUTHOR,
                EMPTY_REVIEW_RATING, EMPTY_REVIEW_CONTENT, EMPTY_PUBLIC_YN, EMPTY_REVIEW_START_DATE, EMPTY_REVIEW_END_DATE,
                CALENDAR_EMPTY_START, CALENDAR_EMPTY_END
        };


        for (BaseResponseStatus status: statusList) {
            if (errors.contains(status.getMessage())){
                return new BaseResponse<>(status);
            }
        }
        return new BaseResponse<>(RESPONSE_ERROR);

    }


}