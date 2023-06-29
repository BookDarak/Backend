package com.bookjeok.bookdarak.repository;

import com.bookjeok.bookdarak.domain.Follow;
import com.bookjeok.bookdarak.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Boolean existsFollowByFollowerUserAndFolloweeUser(User followerUser, User followeeUser);
    Follow findFollowByFollowerUserAndFolloweeUser(User followerUser, User followeeUser);
    //팔로워 조회
    List<Follow> findAllByFolloweeUser(User followeeUser);
    //팔로잉 조회
    List<Follow> findAllByFollowerUser(User followerUser);

    Long countByFollowerUser(User followerUser);
}
