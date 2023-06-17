package com.bookjeok.bookdarak.controller;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.dto.user.FollowRes;
import com.bookjeok.bookdarak.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    @PostMapping("/follows/{followerId}/{followeeId}")
    public BaseResponse<String> followUser(@PathVariable Long followerId, @PathVariable Long followeeId) {
        return followService.followUser(followerId, followeeId);
    }

    @DeleteMapping("/follows/{followerId}/{followeeId}")
    public BaseResponse<String> unfollowUser(@PathVariable Long followerId, @PathVariable Long followeeId){
        return followService.unfollowUser(followerId, followeeId);
    }

    @GetMapping("/follows/{followerId}/{followeeId}")
    public BaseResponse<String> getFollowStatus(@PathVariable Long followerId, @PathVariable Long followeeId){
        return followService.getFollowStatus(followerId, followeeId);
    }

    @GetMapping("/follows/{userId}")
    public BaseResponse<List<FollowRes>> getUserFollowers(@PathVariable Long userId) {
        return followService.getUserFollowers(userId);
    }
}