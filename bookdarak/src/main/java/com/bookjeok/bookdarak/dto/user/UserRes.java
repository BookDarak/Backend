package com.bookjeok.bookdarak.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserRes {
    @Getter
    @AllArgsConstructor
    public static class UserIdRes{
        private Long id;
    }
}