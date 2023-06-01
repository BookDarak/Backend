package com.bookjeok.bookdarak.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
public class UserReq {
    @Getter @AllArgsConstructor
    public static class SignupReq{
        private String email;
        private String password;
        private String name;
        private Integer age;
        private String introduction;
        private String profile_url;
    }
    @Getter @AllArgsConstructor
    public static class LoginReq{
        private String email;
        private String password;
    }
}
