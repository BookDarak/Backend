package com.bookjeok.bookdarak.dto.user;

import com.bookjeok.bookdarak.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserRes {
    @Getter
    @AllArgsConstructor
    public static class Login {
        private Long id;
        private String token;
    }

    @Getter
    @AllArgsConstructor
    public static class Signup{
        private Long id;
    }

    @Getter
    @AllArgsConstructor
    public static class UserInfo {
        private String name;
        private String introduction;
        private String profileUrl;
        private int age;
        private Long reviewCount;
        private Long bookmarkCount;
        private Long followCount;

        public UserInfo(User user, Long reviewCount, Long bookmarkCount, Long followCount) {
            name = user.getName();
            introduction = user.getIntroduction();
            profileUrl = user.getProfileUrl();
            age = user.getAge();
            this.reviewCount = reviewCount;
            this.bookmarkCount = bookmarkCount;
            this.followCount = followCount;
        }
    }
}
