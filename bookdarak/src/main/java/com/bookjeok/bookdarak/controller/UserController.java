package com.bookjeok.bookdarak.controller;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.base.BaseResponseStatus;
import com.bookjeok.bookdarak.dto.user.UserReq;
import com.bookjeok.bookdarak.dto.user.UserRes;
import com.bookjeok.bookdarak.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/signup")
    public BaseResponse<UserRes.UserIdRes> signup(@RequestBody UserReq.SignupReq request){
        if (request.getEmail().isBlank()){
            return new BaseResponse<>(BaseResponseStatus.EMPTY_USER_EMAIL);
        }
        if (!userService.isValidEmailFormat(request.getEmail())){
            return new BaseResponse<>(BaseResponseStatus.INVALID_USER_EMAIL);
        }
        if (request.getPassword().isBlank()){
            return new BaseResponse<>(BaseResponseStatus.EMPTY_USER_PASSWORD);
        }
        if (!userService.isValidPasswordFormat(request.getPassword())) {
            return new BaseResponse<>(BaseResponseStatus.INVALID_FORMAT_PASSWORD);
        }

        if (request.getName().isBlank()){
            return new BaseResponse<>(BaseResponseStatus.EMPTY_USER_NICKNAME);
        }
        if (request.getAge() == null){
            return new BaseResponse<>(BaseResponseStatus.EMPTY_USER_AGE);
        }
        if (request.getIntroduction().isBlank()){
            return new BaseResponse<>(BaseResponseStatus.EMPTY_USER_INTRO);
        }
        return userService.signup(request);
    }
    @PostMapping("/login")
    public BaseResponse<UserRes.UserIdRes> login(@RequestBody UserReq.LoginReq request){
        if (request.getEmail().isBlank())
            return new BaseResponse<>(BaseResponseStatus.EMPTY_USER_EMAIL);
        if (!userService.isValidEmailFormat(request.getEmail())){
            return new BaseResponse<>(BaseResponseStatus.INVALID_USER_EMAIL);
        }
        if (request.getPassword().isBlank())
            return new BaseResponse<>(BaseResponseStatus.EMPTY_USER_PASSWORD);

        return userService.login(request);
    }
    @DeleteMapping("/user/{id}")
    public BaseResponse<UserRes.UserIdRes> deleteUser(@PathVariable("id") Long id){
        return userService.deleteUser(id);
    }
}