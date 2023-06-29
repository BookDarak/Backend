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
        User followerUser = userRepository.findById(followerId).orElseThrow();
        User followeeUser = userRepository.findById(followeeId).orElseThrow();

        if (followRepository.existsFollowByFollowerUserAndFolloweeUser(followerUser, followeeUser)) {
            return new BaseResponse<>(FOLLOW_ALREADY_ADDED);
        }

        Follow follow = Follow.builder()
                .followerUser(followerUser)
                .followeeUser(followeeUser)
                .build();
        followRepository.save(follow);
        return new BaseResponse<>("팔로우를 추가했습니다.");
    }

    public BaseResponse<String> unfollowUser(Long followerId, Long followeeId) {
        Follow follow = getFollowEntity(followeeId, followerId);
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
        //유저 조회
        if (!userRepository.existsById(userId)) {
            return new BaseResponse<>(NOT_EXIST_USER_ID);
        }
        User user = userRepository.findById(userId).orElseThrow();

        //유저의 팔로워들 조회
        List<Follow> follows= followRepository.findAllByFolloweeUser(user);

        return getFollowResList(follows, true);
    }

    public BaseResponse<List<FollowRes>> getUserFollowings(Long userId) {
        if (!userRepository.existsById(userId)) {
            return new BaseResponse<>(NOT_EXIST_USER_ID);
        }
        User user = userRepository.findById(userId).orElseThrow();

        //유저의 팔로워들 조회
        List<Follow> follows= followRepository.findAllByFollowerUser(user);


        return getFollowResList(follows, false);
    }

    public BaseResponse<List<FollowRes>> getFollowResList(List<Follow> follows, Boolean isFollower){
        List<FollowRes> followResList = new ArrayList<>();
        Long userId;
        for (Follow follow : follows) {

            if (isFollower){ //팔로워 조회
                userId = follow.getFollowerUser().getId();
            } else { //팔로잉 조회
                userId = follow.getFolloweeUser().getId();
            }
            User followUser = userRepository.findById(userId).orElseThrow();
            followResList.add(FollowRes.builder()
                    .followerId(followUser.getId())
                    .followerName(followUser.getName())
                    .followerImgUrl(followUser.getProfileUrl())
                    .build());
        }
        return new BaseResponse<>(followResList);
    }

    public Follow getFollowEntity(Long followerId, Long followeeId){
        User followerUser = userRepository.findById(followerId).orElseThrow();
        User followeeUser = userRepository.findById(followeeId).orElseThrow();

        return followRepository.findFollowByFollowerUserAndFolloweeUser(followerUser, followeeUser);
    }


}