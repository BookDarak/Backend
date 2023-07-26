package com.bookjeok.bookdarak.controller;

import com.bookjeok.bookdarak.dto.gpt.QuestionRequestDto;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/chat-gpt")
public class GptController {
    @Value("${spring.openai.api-key}")
    private String apiKey;


    @ApiOperation(value="chatGPT")
    @PostMapping("/question")
    public String sendQuestion(@Valid @RequestBody QuestionRequestDto requestDto) {
        System.out.println("sendQuestion");
        OpenAiService service = new OpenAiService(apiKey, Duration.ofSeconds(60));

        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage("user", requestDto.getQuestion());
        messages.add(chatMessage);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .temperature(1.0)
                .n(1)
                .stream(Boolean.FALSE)
                .maxTokens(500)
                .presencePenalty(0.0)
                .frequencyPenalty(0.0)
                .build();

        String responseMessage = service.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage().getContent();

        return responseMessage;
    }
}
