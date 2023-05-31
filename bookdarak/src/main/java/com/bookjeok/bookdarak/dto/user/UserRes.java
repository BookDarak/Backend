package com.bookjeok.bookdarak.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserRes {
    @Getter
    @AllArgsConstructor
    public static class SignupRes{
        private Long id;
    }

    @Getter
    @AllArgsConstructor
    public static class LoginRes{
        private Long id;
    }
}
