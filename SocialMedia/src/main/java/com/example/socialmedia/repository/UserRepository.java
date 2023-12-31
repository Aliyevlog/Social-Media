package com.example.socialmedia.repository;

import com.example.socialmedia.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findByNameContaining (String name);

    Page<User> findByUsernameContaining (String username, Pageable pageable);
}
