package com.bookjeok.bookdarak.domain;

import com.bookjeok.bookdarak.base.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    private String name;
    private String author;
    private String publisher;
    private LocalDate publishedDate;

    private int price;
    private String introduction;
    private String siteUrl;
    private String imgUrl;

    public Book(String name, String author, String publisher, LocalDate publishedDate, int price, String introduction, String siteUrl, String imgUrl) {
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.price = price;
        this.introduction = introduction;
        this.siteUrl = siteUrl;
        this.imgUrl = imgUrl;
    }
}
