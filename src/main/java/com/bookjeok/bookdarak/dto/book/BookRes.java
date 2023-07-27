package com.bookjeok.bookdarak.dto.book;

import com.bookjeok.bookdarak.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class BookRes {
    @Getter @AllArgsConstructor
    public static class BookId {
        private Long id;
    }

    @Getter @AllArgsConstructor
    public static class BookInfo {
        private String name;
        private List<String> authorList;
        private String isbn;
        private String imgUrl;

        public BookInfo(Book book){
            this.name = book.getName();
            this.authorList = book.stringToList(book.getAuthor());
            this.isbn = book.getIsbn();
            this.imgUrl = book.getImgUrl();
        }
    }
}
