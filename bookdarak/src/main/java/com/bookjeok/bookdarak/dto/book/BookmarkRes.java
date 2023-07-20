package com.bookjeok.bookdarak.dto.book;


import com.bookjeok.bookdarak.domain.Book;
import com.bookjeok.bookdarak.domain.Bookmark;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@AllArgsConstructor @Builder
public class BookmarkRes {
    public Long bookId;
    public String name;
    public String bookImgUrl;

    public static BookmarkRes of(Bookmark b){
        return BookmarkRes.builder()
                .bookId(b.getBook().getId())
                .name(b.getBook().getName())
                .bookImgUrl(b.getBook().getImgUrl())
                .build();
    }
}


