package com.cc.creativecraze.repository;

import com.cc.creativecraze.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}

