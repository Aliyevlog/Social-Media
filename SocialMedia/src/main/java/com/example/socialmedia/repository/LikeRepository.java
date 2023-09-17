package com.example.socialmedia.repository;

import com.example.socialmedia.entity.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserIdAndPostId(Long userId, Long postId);

    Long countByPostIdAndReaction(Long postId, Boolean reaction);

    Page<Like> findLikeByPostIdAndReaction (Long postId, Boolean reaction, Pageable pageable);
}
