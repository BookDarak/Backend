package com.bookjeok.bookdarak.base;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "성공"),

    UPDATE_SUCCESS(true, 1001, "수정에 성공하였습니다."),

    DELETE_SUCCESS(true, 1002, "삭제를 완료하였습니다."),

    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),

    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),

    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),

    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),

    EMPTY_USER_EMAIL(false, 2014, "이메일을 입력해주세요."),

    INVALID_USER_EMAIL(false, 2015, "이메일 형식을 확인해주세요."),

    DUPLICATED_USER_EMAIL(false,2016,"중복된 이메일입니다."),

    EMPTY_USER_PASSWORD(false, 2017, "비밀번호를 입력해주세요."),

    INVALID_FORMAT_PASSWORD(false, 2018, "비밀번호는 숫자, 영문, 특수문자를 포함해서 8자~16자 이내로 설정해주세요."),

    FAILED_TO_SIGN_UP(false, 2019, "회원가입에 실패하였습니다."),

    EMPTY_USER_NICKNAME(false,2022,"닉네임을 입력해주세요."),

    FAILED_TO_LOGIN(false, 2023, "로그인에 실패하였습니다."),

    NOT_EXIST_USER_ID(false, 2024, "존재하지 않는 유저 id입니다."),

    NOT_EXIST_NICKNAME(false, 2025, "존재하지 않는 닉네임입니다."),

    NOT_EXIST_EMAIL(false, 2026, "존재하지 않는 이메일입니다."),

    NOT_CORRECT_PASSWORD(false, 2027, "비밀번호가 일치하지 않습니다."),

    ALREADY_DELETED_USER(false, 2028, "이미 탈퇴된 유저입니다."),

    DUPLICATED_BOOK_NAME(false, 2029, "이미 등록된 책입니다."),

    EMPTY_BOOK_NAME(false, 2030, "도서명을 입력해주세요."),

    EMPTY_BOOK_AUTHOR(false, 2031, "저자를 입력해주세요."),

    EMPTY_BOOK_ISBN(false, 2032, "isbn을 입력해주세요."),

    NOT_EXIST_BOOK_ID(false, 2033, "존재하지 않는 책 id입니다."),

    EMPTY_USER_GENDER(false, 2034, "성별을 입력해주세요."),

    INVALID_USER_GENDER(false, 2035, "성별 형식이 올바르지 않습니다."),

    EMPTY_USER_AGE(false, 2036, "나이를 입력해주세요."),

    EMPTY_USER_INTRO(false, 2037, "자기소개를 입력해주세요."),


    EMPTY_REVIEW_RATING(false, 2040, "평점을 입력해주세요."),

    EMPTY_REVIEW_CONTENT(false, 2041, "서평 내용을 입력해주세요."),

    EMPTY_PUBLIC_YN(false, 2042, "공개여부를 입력해주세요."),

    WRONG_REVIEW_PUBLIC_FORMAT(false, 2043, "공개여부 형식이 올바르지 않습니다."),

    EMPTY_REVIEW_START_DATE(false, 2044, "시작 독서일을 선택해주세요."),

    EMPTY_REVIEW_END_DATE(false, 2045, "완료 독서일을 선택해주세요."),

    INVALID_DATE_INTERVAL(false,2046, "시작일은 완료일보다 앞서야 합니다."),


    NOT_EXIST_REVIEW(false, 2050, "해당 서평이 존재하지 않습니다."),

    BOOKMARK_ALREADY_ADDED(false, 2051, "이미 북마크에 추가된 책입니다."),

    BOOKMARK_ALREADY_DELETED(false, 2052, "이미 삭제된 북마크 책입니다."),

    BOOKMARK_NOT_EXISTS(false, 2053, "북마크에 추가된 책이 없습니다."),

    FOLLOW_INVALID_INPUT(false,2054,"팔로워와 팔로이 아이디는 동일할 수 없습니다."),

    FOLLOW_ALREADY_ADDED(false, 2055, "이미 팔로우한 유저입니다."),
    FOLLOW_ALREADY_DELETED(false, 2056, "이미 팔로우 취소한 유저입니다."),

    DUPLICATED_USER_NAME(false, 2057, "중복된 유저 닉네임입니다."),


    RECOMMEND_ALREADY_ADDED(false,2058, "이미 추천한 서평입니다."),

    EMPTY_CAL_STARTD(false,2059,"캘린더 시작 날짜를 입력해주세요."),

    EMPTY_CAL_ENDD(false,2060,"캘린더 종료 날짜를 입력해주세요."),

    INCORRECT_OLD_PW(false,2061,"기존 비밀번호를 올바르게 입력해주세요."),

    INCORRECT_EMAIL(false, 2062, "이메일이 일치하지 않습니다."),

    RECOMMEND_ALREADY_DELETED(false,2063,"이미 추천을 삭제했습니다."),

    NOT_EXIST_REVIEW_ID(false,2064,"존재하지 않는 리뷰입니다."),

    EMPTY_GPT_QUESTION(false, 2065, "질문을 입력해주세요."),

    NOT_EXIST_BOARD_ID(false, 2066, "존재하지 않는 게시판입니다."),

    NOT_EXIST_BOARDCOMMENT_ID(false, 2067, "존재하지 않는 게시판 댓글입니다"),

    INVALID_USER_AGE(false, 2068, "나이를 올바르게 입력해주세요."),


    /**
     * 3000 : Response 오류
     */

    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),

    REVIEW_ALREADY_EXISTS(false,3040,"해당 도서에 대한 서평이 이미 존재합니다."),

    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),

    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    MODIFY_FAIL_USERNAME(false,4014,"유저네임 수정 실패"),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),

    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다.");




    private final boolean isSuccess;
    private final int code;
    private final String message;

    BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}