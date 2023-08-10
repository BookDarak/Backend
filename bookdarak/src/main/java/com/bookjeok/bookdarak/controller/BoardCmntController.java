package com.bookjeok.bookdarak.controller;

import com.bookjeok.bookdarak.service.BoardCmntService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardCmntController {
    private final BoardCmntService boardCmntService;

}
