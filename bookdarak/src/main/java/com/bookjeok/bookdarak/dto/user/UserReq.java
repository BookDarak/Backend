package com.bookjeok.bookdarak.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
public class UserReq {
    @Getter @AllArgsConstructor
    public static class SignupReq{
        private String email;
        private String password;
        private String name;
        private int age;
        private String introduction;
        private String profile_url;
    }
}
