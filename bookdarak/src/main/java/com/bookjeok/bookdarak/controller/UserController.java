package com.bookjeok.bookdarak.controller;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.dto.user.UserReq;
import com.bookjeok.bookdarak.dto.user.UserRes;
import com.bookjeok.bookdarak.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/login")
    public BaseResponse<UserRes.LoginRes> login(@RequestBody UserReq.LoginReq request){
        return userService.login(request);
    }
    @PostMapping("/signup")
    public BaseResponse<UserRes.SignupRes> signup(@RequestBody UserReq.SignupReq request){
        return userService.signup(request);
    }
}
