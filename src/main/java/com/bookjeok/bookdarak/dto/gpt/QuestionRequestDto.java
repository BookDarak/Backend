package com.bookjeok.bookdarak.dto.gpt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter @RequiredArgsConstructor
public class QuestionRequestDto {
    @NotBlank(message="질문을 입력해주세요.")
    private String question;
}
