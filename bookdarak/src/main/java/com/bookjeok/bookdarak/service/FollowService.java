package com.bookjeok.bookdarak.service;

import com.bookjeok.bookdarak.base.BaseResponse;
import com.bookjeok.bookdarak.domain.Follow;
import com.bookjeok.bookdarak.domain.User;
import com.bookjeok.bookdarak.dto.user.FollowRes;
import com.bookjeok.bookdarak.repository.FollowRepository;
import com.bookjeok.bookdarak.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.bookjeok.bookdarak.base.BaseResponseStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public BaseResponse<String> followUser(Long followerId, Long followeeId) {
        User followerUser = getFollowerUser(followerId);
        User followeeUser = getFolloweeUser(followeeId);

        if (followRepository.existsFollowByFollowerUserAndFolloweeUser(followerUser, followeeUser)) {
            return new BaseResponse<>(FOLLOW_ALREADY_ADDED);
        }

        saveFollowEntity(followerUser, followeeUser);
        return new BaseResponse<>("팔로우를 추가했습니다.");
    }

    public BaseResponse<String> unfollowUser(Long followerId, Long followeeId) {
        Follow follow = getFollowEntity(followerId, followeeId);
        if (follow==null){
            return new BaseResponse<>(FOLLOW_ALREADY_DELETED);
        }
        followRepository.delete(follow);
        return new BaseResponse<>("팔로우를 취소했습니다.");

    }

    @Transactional(readOnly = true)
    public BaseResponse<String> getFollowStatus(Long followerId, Long followeeId) {
        if (getFollowEntity(followerId, followeeId)==null){
           return new BaseResponse<>("false");
        } else {
            return new BaseResponse<>("true");
        }
    }

    @Transactional(readOnly = true)
    public BaseResponse<List<FollowRes>> getUserFollowers(Long userId){
        User user = userRepository.findById(userId).orElse(null);
        //유저 조회
        if (user==null) {
            return new BaseResponse<>(NOT_EXIST_USER_ID);
        }

        //유저의 팔로워들 조회
        List<Follow> follows= followRepository.findAllByFolloweeUser(user);

        return getFollowResList(follows, true);
    }

    public BaseResponse<List<FollowRes>> getUserFollowings(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user==null) {
            return new BaseResponse<>(NOT_EXIST_USER_ID);
        }
        //유저의 팔로워들 조회
        List<Follow> follows= followRepository.findAllByFollowerUser(user);


        return getFollowResList(follows, false);
    }

    public BaseResponse<List<FollowRes>> getFollowResList(List<Follow> follows, Boolean isFollower){
        List<FollowRes> followResList = new ArrayList<>();
        Long userId;
        for (Follow follow : follows) {
            userId = isFollower ? follow.getFollowerUser().getId() : follow.getFolloweeUser().getId(); //팔로워 or 팔로이 조회

            User followUser = getFollowerUser(userId);
            followResList.add(FollowRes.builder()
                    .followerId(followUser.getId())
                    .followerName(followUser.getName())
                    .followerImgUrl(followUser.getProfileUrl())
                    .build());
        }
        return new BaseResponse<>(followResList);
    }

    public User getFollowerUser(Long followerId){
        return userRepository.findById(followerId).orElseThrow();
    }

    public User getFolloweeUser(Long followeeId){
        return userRepository.findById(followeeId).orElseThrow();
    }

    public Follow getFollowEntity(Long followerId, Long followeeId){
        User followerUser = getFollowerUser(followerId);
        User followeeUser = getFolloweeUser(followeeId);

        return followRepository.findFollowByFollowerUserAndFolloweeUser(followerUser, followeeUser);
    }

    private void saveFollowEntity(User followerUser, User followeeUser) {
        Follow follow = Follow.builder()
                .followerUser(followerUser)
                .followeeUser(followeeUser)
                .build();
        followRepository.save(follow);
    }
}