package com.bookjeok.bookdarak.controller;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.dto.user.UserReq;
import com.bookjeok.bookdarak.dto.user.UserRes;
import com.bookjeok.bookdarak.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/login")
    public BaseResponse<UserRes.UserIdRes> login(@RequestBody UserReq.LoginReq request){
        return userService.login(request);
    }
    @PostMapping("/signup")
    public BaseResponse<UserRes.UserIdRes> signup(@RequestBody UserReq.SignupReq request){
        return userService.signup(request);
    }
    @DeleteMapping("/user/{id}")
    public BaseResponse<UserRes.UserIdRes> deleteUser(@PathVariable("id") Long id){
        return userService.deleteUser(id);
    }
}
