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
    private String isbn;
    private String imgUrl;

    public Book(String name, String author,  String isbn, String imgUrl) {
        this.name = name;
        this.author = author;
        this.isbn = isbn;
        this.imgUrl = imgUrl;
    }
}
