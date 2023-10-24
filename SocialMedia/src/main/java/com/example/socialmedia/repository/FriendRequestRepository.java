package com.example.socialmedia.repository;

import com.example.socialmedia.entity.FriendRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    Page<FriendRequest> findByReceiverUsername (String username, Pageable pageable);

    boolean existsBySenderIdAndReceiverId (Long senderId, Long receiverId);

    Optional<FriendRequest> findBySenderIdAndReceiverId (Long senderId, Long receiverId);

    @Transactional
    void deleteAllBySenderIdOrReceiverId (Long senderId, Long receiverId);
}
