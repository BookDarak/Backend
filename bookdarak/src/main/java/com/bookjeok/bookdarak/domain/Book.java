package com.bookjeok.bookdarak.domain;

import com.bookjeok.bookdarak.base.BaseEntity;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity @Getter
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
}
