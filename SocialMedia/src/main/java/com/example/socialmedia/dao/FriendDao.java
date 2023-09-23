package com.example.socialmedia.dao;

import com.example.socialmedia.entity.Friend;
import org.springframework.data.domain.Page;

public interface FriendDao {
    Friend add(Friend friend);

    void remove(Long id);

    Page<Friend> getAll(Long userId, Integer page, Integer limit);

    boolean existsByUser1IdAndUser2Id(Long user1Id, Long user2Id);
}

