package com.example.socialmedia.dao;

import com.example.socialmedia.entity.FriendRequest;
import com.example.socialmedia.exception.NotFoundException;
import org.springframework.data.domain.Page;

public interface FriendRequestDao {
    FriendRequest add(FriendRequest request);

    void remove(Long id);

    Page<FriendRequest> getAll(Integer page, Integer limit);

    boolean existsBySenderIdAndReceiverId(Long senderId, Long receiverId);

    FriendRequest getBySenderIdAndReceiverId(Long senderId, Long receiverId) throws NotFoundException;
}
