package com.bookjeok.bookdarak.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserReq {
    @Getter @AllArgsConstructor
    public static class SignupReq{
        @NotBlank(message="이메일을 입력해주세요.")
        private String email;

        @NotBlank(message="비밀번호를 입력해주세요.")
        private String password;

        @NotBlank(message="이름을 입력해주세요.")
        private String name;
        @NotNull(message="나이를 입력해주세요.")
        private Integer age;
        @NotBlank(message="자기소개를 입력해주세요.")
        private String introduction;
        private String profile_url;
    }
    @Getter @AllArgsConstructor
    public static class LoginReq{
        @NotBlank(message="이메일을 입력해주세요.")
        private String email;

        @NotBlank(message="비밀번호를 입력해주세요.")
        private String password;
    }
}
