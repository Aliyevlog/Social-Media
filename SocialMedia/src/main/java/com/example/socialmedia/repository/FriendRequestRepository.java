package com.example.socialmedia.repository;

import com.example.socialmedia.entity.FriendRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    Page<FriendRequest> findByReceiverUsername (String username, Pageable pageable);
}
