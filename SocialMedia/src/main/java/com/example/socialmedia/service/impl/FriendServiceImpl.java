package com.example.socialmedia.service.impl;

import com.example.socialmedia.dao.FriendDao;
import com.example.socialmedia.dao.UserDao;
import com.example.socialmedia.dto.response.PageResponse;
import com.example.socialmedia.dto.response.ShortFriendResponse;
import com.example.socialmedia.entity.Friend;
import com.example.socialmedia.exception.NotFoundException;
import com.example.socialmedia.mapper.FriendMapper;
import com.example.socialmedia.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
    private final FriendDao friendDao;
    private final UserDao userDao;
    private final FriendMapper friendMapper;

    @Override
    public Friend add(Long user1Id, Long user2Id) throws NotFoundException {
        Friend friend = Friend.builder()
                .user1(userDao.getById(user1Id))
                .user2(userDao.getById(user2Id))
                .build();

        return friendDao.add(friend);
    }

    @Override
    public void remove(Long id) {
        friendDao.remove(id);
    }

    @Override
    public PageResponse<ShortFriendResponse> getAll(Long userId, Integer page, Integer limit) {
        Page<Friend> friends = friendDao.getAll(userId, page, limit);

        return friendMapper.map(friends, userId);
    }

    @Override
    public boolean isFriend(Long user1Id, Long user2Id) {
        return friendDao.existsByUser1IdAndUser2Id(user1Id, user2Id) ||
               friendDao.existsByUser1IdAndUser2Id(user2Id, user1Id);
    }
}
