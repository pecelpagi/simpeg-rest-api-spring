package com.galuhrmdh.simpegrestapi.repository;

import com.galuhrmdh.simpegrestapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findFirstByToken(String token);

    Optional<User> findFirstByUsername(String username);
}
