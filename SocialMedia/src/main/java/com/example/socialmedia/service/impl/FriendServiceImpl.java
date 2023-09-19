package com.example.socialmedia.service.impl;

import com.example.socialmedia.entity.Friend;
import com.example.socialmedia.exception.NotFoundException;
import com.example.socialmedia.repository.FriendRepository;
import com.example.socialmedia.service.FriendService;
import com.example.socialmedia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
    private final FriendRepository friendRepository;
    private final UserService userService;

    @Override
    public Friend add(Long user1Id, Long user2Id) throws NotFoundException {
        Friend friend = Friend.builder()
                .user1(userService.getById(user1Id))
                .user2(userService.getById(user2Id))
                .build();

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
}
