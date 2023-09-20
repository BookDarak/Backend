package com.bookjeok.bookdarak.repository;

import com.bookjeok.bookdarak.domain.Board;
import com.bookjeok.bookdarak.domain.BoardCmnt;
import com.bookjeok.bookdarak.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCmntRepository extends JpaRepository<BoardCmnt, Long> {
    Page<BoardCmnt> findByBoardId(Long boardId, Pageable pageable);

    void deleteByUser(User user);
    void deleteBoardCmntByBoard(Board board);

    void deleteByBoard(Board board);
}
