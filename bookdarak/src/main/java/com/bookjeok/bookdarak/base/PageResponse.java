package com.bookjeok.bookdarak.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageResponse<T> {
    private int currentPage;
    private int totalPages;
    private long totalItems;
    private List<T> items;

    //Page<T> 객체 -> PageResponse<T> 객체
    public static <T> PageResponse<T> fromPage(Page<T> page){
        return new PageResponse<>(
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getContent()
        );
    }
}
