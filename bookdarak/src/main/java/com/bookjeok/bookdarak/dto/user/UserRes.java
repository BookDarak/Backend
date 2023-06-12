package com.bookjeok.bookdarak.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserRes {
    @Getter
    @AllArgsConstructor
    public static class Login {
        private Long id;
        private String token;
    }

    @Getter
    @AllArgsConstructor
    public static class Signup{
        private Long id;
    }
}
