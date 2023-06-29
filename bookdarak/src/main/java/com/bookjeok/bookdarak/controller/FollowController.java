package com.bookjeok.bookdarak.controller;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.dto.user.FollowRes;
import com.bookjeok.bookdarak.service.FollowService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    @ApiOperation(value = "팔로우 하기")
    @PostMapping("/follows/{followerId}/{followeeId}")
    public BaseResponse<String> followUser(@PathVariable Long followerId, @PathVariable Long followeeId) {
        return followService.followUser(followerId, followeeId);
    }

    @ApiOperation(value = "팔로우 취소")
    @DeleteMapping("/follows/{followerId}/{followeeId}")
    public BaseResponse<String> unfollowUser(@PathVariable Long followerId, @PathVariable Long followeeId) {
        return followService.unfollowUser(followerId, followeeId);
    }

    @ApiOperation(value = "팔로우 여부 확인")
    @GetMapping("/follows/{followerId}/{followeeId}")
    public BaseResponse<String> getFollowStatus(@PathVariable Long followerId, @PathVariable Long followeeId) {
        return followService.getFollowStatus(followerId, followeeId);
    }

    @ApiOperation(value = "팔로워 조회", notes = "특정 유저에게 팔로잉 당하는 유저들 조회")
    @GetMapping("/follows/followers/{userId}")
    public BaseResponse<List<FollowRes>> getUserFollowers(@PathVariable Long userId) {
        return followService.getUserFollowers(userId);
    }

    @ApiOperation(value = "팔로잉 조회", notes = "특정 유저가 팔로우하는 유저들 조회")
    @GetMapping("/follows/followees/{userId}")
    public BaseResponse<List<FollowRes>> getUserFollowings(@PathVariable Long userId) {
        return followService.getUserFollowings(userId);
    }
}