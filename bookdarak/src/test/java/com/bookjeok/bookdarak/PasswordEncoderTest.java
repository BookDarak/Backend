package com.bookjeok.bookdarak;

import com.bookjeok.bookdarak.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PasswordEncoderTest {

    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("패스워드 암호화 테스트")
    void passwordEncode(){
        //given
        String rawPassword = "sugar123!";

        //when
        String encodedPassword = passwordEncoder.encode(rawPassword);

        //then
        assertAll(
                () -> assertNotEquals(rawPassword, encodedPassword), //둘이 다른지
                () -> assertTrue(passwordEncoder.matches(rawPassword, encodedPassword)) //복호화한 암호가 기존과 동일한지
        );
    }
}
