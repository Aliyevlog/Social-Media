package com.example.socialmedia.service;

import com.example.socialmedia.entity.FriendRequest;
import com.example.socialmedia.exception.NotFoundException;
import org.springframework.data.domain.Page;

public interface FriendRequestService {
    FriendRequest add (Long receiverId) throws NotFoundException;

    void remove (Long id);

    FriendRequest accept (Long id) throws NotFoundException;

    Page<FriendRequest> getAll (Integer page, Integer limit);
}
