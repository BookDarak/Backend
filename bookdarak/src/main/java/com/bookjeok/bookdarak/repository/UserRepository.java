package com.bookjeok.bookdarak.repository;

import  com.bookjeok.bookdarak.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByName(String name);
    User findByEmail(String email);

    List<User> findByAgeBetween(int lowerBound, int upperBound);
    List<User> findByGender(String gender);
}
