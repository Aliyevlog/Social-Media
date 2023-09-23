package com.example.socialmedia.dao.impl;

import com.example.socialmedia.dao.FriendDao;
import com.example.socialmedia.entity.Friend;
import com.example.socialmedia.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendDaoImpl implements FriendDao {
    private final FriendRepository friendRepository;

    @Override
    public Friend add(Friend friend) {
        return friendRepository.save(friend);
    }

    @Override
    public void remove(Long id) {
        friendRepository.deleteById(id);
    }

    @Override
    public Page<Friend> getAll(Long userId, Integer page, Integer limit) {
        Pageable pageable = page != null && limit != null ? PageRequest.of(page - 1, limit) :
                PageRequest.of(0, 10);
        return friendRepository.findByUser1IdOrUser2Id(userId, userId, pageable);
    }

    @Override
    public boolean existsByUser1IdAndUser2Id(Long user1Id, Long user2Id) {
        return friendRepository.existsByUser1IdAndUser2Id(user1Id, user2Id);
    }
}
