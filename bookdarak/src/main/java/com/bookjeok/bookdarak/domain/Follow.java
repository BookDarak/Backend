package com.bookjeok.bookdarak.domain;

import com.bookjeok.bookdarak.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    private User followerUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followee_id")
    private User followeeUser;

    @Builder
    public Follow(User followeeUser, User followerUser){
        this.followerUser = followerUser;
        this.followeeUser = followeeUser;
    }
    public static List<Long> getFollowers(List<Follow> follows){
        List<Long> followerIdList = new ArrayList<>();
        for (Follow follow: follows) {
            followerIdList.add(follow.getFollowerUser().getId());
        }
        return followerIdList;
    }
}
