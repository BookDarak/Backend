package com.bookjeok.bookdarak.dto.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor @Getter @Builder
public class FollowRes {
    private Long followerId;
    private String followerName;
    private String followerImgUrl;

}
