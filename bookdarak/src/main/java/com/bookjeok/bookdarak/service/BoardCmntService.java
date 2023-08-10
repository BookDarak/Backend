package com.bookjeok.bookdarak.service;

import com.bookjeok.bookdarak.repository.BoardCmntRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardCmntService {
    private final BoardCmntRepository boardCmntRepository;
}
