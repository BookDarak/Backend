package com.bookjeok.bookdarak.domain;

import com.bookjeok.bookdarak.base.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;


    private String email;
    private String password;
    private String name;
    private Integer age;
    private String introduction;
    private String profileUrl;

    @Builder
    public User(String email, String password, String name, Integer age, String introduction, String profileUrl) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
        this.introduction = introduction;
        this.profileUrl = profileUrl;
    }
}
