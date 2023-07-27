package com.bookjeok.bookdarak.domain;

import com.bookjeok.bookdarak.base.BaseEntity;
import com.bookjeok.bookdarak.dto.book.BookReq;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

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


    public Book(BookReq.bookInfo dto){
        this.name = dto.getName();
        this.author = listToString(dto.getAuthorList());
        this.isbn = dto.getIsbn();
        this.imgUrl = dto.getImgUrl();
    }

    // author 타입 변환 함수
    private String listToString(List<String> authorList){
        String author = "";
        for (String item : authorList) {
            author += item + ",";
        }
        return author;
    }

    public List<String> stringToList(String author){
        return Arrays.asList(author.split("\\s*,\\s*"));
    }
}
