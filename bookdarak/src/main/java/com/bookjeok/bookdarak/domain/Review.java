package com.bookjeok.bookdarak.domain;

import com.bookjeok.bookdarak.base.BaseEntity;
import com.bookjeok.bookdarak.dto.review.ReviewReq;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private BigDecimal rating;
    private String content;
    private String phrase;

    private Character publicYn; // Y, N
    private int likeCount;

    private LocalDate startDate;
    private LocalDate endDate;


    public Review(User user, Book book, BigDecimal rating, String content, String phrase, Character publicYn,  LocalDate startDate, LocalDate endDate) {
        this.user = user;
        this.book = book;
        this.rating = rating;
        this.content = content;
        this.phrase = phrase;
        this.publicYn = publicYn;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //PATCH
    public void updateReview(ReviewReq.UpdateReviewReq updateDto){
        if (updateDto.getRating()!=null){
            this.rating = updateDto.getRating();
        }
        if (updateDto.getContent()!=null){
            this.content = updateDto.getContent();
        }
        if (updateDto.getPhrase()!=null){
            this.phrase = updateDto.getPhrase();
        }
        if (updateDto.getPublicYn()!=null){
            this.publicYn = updateDto.getPublicYn();
        }
        if (updateDto.getStartDate()!=null){
            this.startDate = updateDto.getStartDate();
        }
        if (updateDto.getEndDate()!=null){
            this.endDate = updateDto.getEndDate();
        }
    }
}
