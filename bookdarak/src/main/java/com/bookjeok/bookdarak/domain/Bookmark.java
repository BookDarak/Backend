package com.bookjeok.bookdarak.domain;

import com.bookjeok.bookdarak.base.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private String bookmarkYn; //Y,N

    @Builder
    public Bookmark(User user, Book book, String bookmarkYn){
        this.user = user;
        this.book = book;
        this.bookmarkYn = bookmarkYn;
    }
}
