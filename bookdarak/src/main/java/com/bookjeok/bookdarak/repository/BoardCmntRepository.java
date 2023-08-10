package com.bookjeok.bookdarak.repository;

import com.bookjeok.bookdarak.domain.BoardCmnt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCmntRepository extends JpaRepository<BoardCmnt, Long> {
}
