package com.example.socialmedia.repository;

import com.example.socialmedia.entity.Comment;
import com.example.socialmedia.entity.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByPost(Post post, Pageable pageable);

    @Transactional
    void deleteAllByUserId (Long id);
}
