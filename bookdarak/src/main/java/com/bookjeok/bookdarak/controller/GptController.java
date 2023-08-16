package com.bookjeok.bookdarak.controller;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.dto.gpt.GptRes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/gpt")
public class GptController {
    @Value("${spring.openai.api-key}")
    private String apiKey;


    @ApiOperation(value="동기부여 명언")
    @PostMapping("/quote")
    public BaseResponse<GptRes.Quote> getRandomQuote() throws JsonProcessingException {
        OpenAiService service = new OpenAiService(apiKey, Duration.ofSeconds(60));
        String question = "Give me a popular line about motivation. Format answer as json object with the keys line, speaker";
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage("user", question);
        messages.add(chatMessage);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .temperature(2.0)
                .n(1)
                .stream(Boolean.FALSE)
                .maxTokens(500)
                .presencePenalty(0.0)
                .frequencyPenalty(0.0)
                .build();

        String content = service.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage().getContent();
        ObjectMapper mapper = new ObjectMapper();
        GptRes.Quote quote = mapper.readValue(content, GptRes.Quote.class);
        return new BaseResponse<>(quote);
    }
}
