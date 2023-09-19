package com.example.socialmedia.service;

import com.example.socialmedia.entity.Friend;
import com.example.socialmedia.exception.NotFoundException;
import org.springframework.data.domain.Page;

public interface FriendService {
    Friend add (Long user1Id, Long user2Id) throws NotFoundException;

    void remove (Long id);

    Page<Friend> getAll (Long userId, Integer page, Integer limit);
}

