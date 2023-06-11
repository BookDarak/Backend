package com.bookjeok.bookdarak.domain;

import com.bookjeok.bookdarak.base.BaseEntity;
import com.bookjeok.bookdarak.dto.review.ReviewReq;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

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

    private String publicYn; // Y, N
    private Integer likeCount;

    private LocalDate startDate;
    private LocalDate endDate;



    public Review(User user, Book book, ReviewReq.AddReviewReq dto){
        this.user = user;
        this.book = book;
        this.rating = dto.getRating();
        this.content = dto.getContent();
        this.phrase = dto.getPhrase();
        this.publicYn = dto.getPublicYn();
        this.startDate = dto.getStartDate();
        this.endDate = dto.getEndDate();
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
        if (updateDto.getLikeCount()!=null){
            this.likeCount = updateDto.getLikeCount();
        }
        if (updateDto.getStartDate()!=null){
            this.startDate = updateDto.getStartDate();
        }
        if (updateDto.getEndDate()!=null){
            this.endDate = updateDto.getEndDate();
        }
    }
}
