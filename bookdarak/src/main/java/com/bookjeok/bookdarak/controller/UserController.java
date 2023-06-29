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
    public BaseResponse<UserRes.Signup> signup(@Valid @RequestBody UserReq.Signup request){
        return userService.signup(request);
    }
    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public BaseResponse<UserRes.Login> login(@Valid @RequestBody UserReq.Login request){
        return userService.login(request);
    }

    @ApiOperation(value = "회원탈퇴")
    @DeleteMapping("/users/{id}")
    public BaseResponse<String> deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }

    @ApiOperation(value = "유저 정보 조회")
    @GetMapping("/users/info/{id}")
    public BaseResponse<UserRes.UserInfo> getUserInfo(@PathVariable Long id){
        return userService.getUserInfo(id);
    }

    @ApiOperation(value = "유저 정보 수정")
    @PatchMapping("/users/info/{id}")
    public BaseResponse<BaseResponseStatus> editUserInfo(@PathVariable Long id, @RequestBody UserReq.UpdateUserInfo updateUserInfoReq){
        return userService.editUserInfo(id, updateUserInfoReq);}
}