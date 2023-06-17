package com.bookjeok.bookdarak.dto.user;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public class FollowRes {
    private Long followerId;
    private String followerName;
    private String followerImgUrl;


}
