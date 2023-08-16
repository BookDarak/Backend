package com.bookjeok.bookdarak.dto.gpt;

import lombok.Getter;

public class GptRes {
    @Getter
    public static class Quote {
        private String line;
        private String speaker;
    }
}
