package com.example.socialmedia.repository;

import com.example.socialmedia.entity.Friend;
import com.example.socialmedia.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    Page<Friend> findByUser1IdOrUser2Id(Long user1Id, Long user2Id, Pageable pageable);
}
