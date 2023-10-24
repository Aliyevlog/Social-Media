package com.example.socialmedia.repository;

import com.example.socialmedia.entity.Post;
import com.example.socialmedia.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByTextContainingAndUserIn (String text, List<User> user, Pageable pageable);

    @Transactional
    void deleteAllByUserId (Long id);
}
