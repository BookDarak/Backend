package com.bookjeok.bookdarak.controller;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.base.BaseResponseStatus;
import com.bookjeok.bookdarak.dto.user.UserReq;
import com.bookjeok.bookdarak.dto.user.UserRes;
import com.bookjeok.bookdarak.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @ApiOperation(value = "회원가입")
    @PostMapping("/signup")
    public BaseResponse<UserRes.UserIdRes> signup(@Valid @RequestBody UserReq.SignupReq request){

        if (userService.isInValidEmailFormat(request.getEmail())){
            return new BaseResponse<>(BaseResponseStatus.INVALID_USER_EMAIL);
        }
        if (userService.isInValidPasswordFormat(request.getPassword())) {
            return new BaseResponse<>(BaseResponseStatus.INVALID_FORMAT_PASSWORD);
        }

        return userService.signup(request);
    }
    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public BaseResponse<UserRes.UserIdRes> login(@Valid @RequestBody UserReq.LoginReq request){
        if (userService.isInValidEmailFormat(request.getEmail())){
            return new BaseResponse<>(BaseResponseStatus.INVALID_USER_EMAIL);
        }
        return userService.login(request);
    }

    @ApiOperation(value = "회원탈퇴")
    @DeleteMapping("/users/{id}")
    public BaseResponse<UserRes.UserIdRes> deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }
}