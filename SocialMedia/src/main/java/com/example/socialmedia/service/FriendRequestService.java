package com.example.socialmedia.service;

import com.example.socialmedia.entity.FriendRequest;
import com.example.socialmedia.exception.IllegalOperationException;
import com.example.socialmedia.exception.NotFoundException;
import org.springframework.data.domain.Page;

public interface FriendRequestService {
    FriendRequest add (Long receiverId) throws NotFoundException, IllegalOperationException;

    void remove (Long id);

    FriendRequest accept (Long senderId) throws NotFoundException;

    Page<FriendRequest> getAll (Integer page, Integer limit);
}
