package com.example.socialmedia.service.impl;

import com.example.socialmedia.config.SecurityConfig;
import com.example.socialmedia.dao.FriendRequestDao;
import com.example.socialmedia.dao.UserDao;
import com.example.socialmedia.dto.response.FriendRequestResponse;
import com.example.socialmedia.dto.response.PageResponse;
import com.example.socialmedia.dto.response.ShortFriendRequestResponse;
import com.example.socialmedia.entity.FriendRequest;
import com.example.socialmedia.entity.User;
import com.example.socialmedia.exception.IllegalOperationException;
import com.example.socialmedia.exception.NotFoundException;
import com.example.socialmedia.mapper.FriendRequestMapper;
import com.example.socialmedia.service.FriendRequestService;
import com.example.socialmedia.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendRequestServiceImpl implements FriendRequestService {
    private final FriendRequestDao friendRequestDao;
    private final UserDao userDao;
    private final MessageSource messageSource;
    private final FriendService friendService;
    private final SecurityConfig securityConfig;
    private final FriendRequestMapper friendRequestMapper;

    @Override
    public FriendRequestResponse add(Long receiverId) throws NotFoundException, IllegalOperationException {
        User sender = userDao.getByUsername(securityConfig.getLoggedInUsername());

        checkUser(sender.getId(), receiverId);
        checkExist(sender.getId(), receiverId);
        FriendRequest friendRequest = FriendRequest.builder()
                .sender(sender)
                .receiver(userDao.getById(receiverId))
                .build();

        return friendRequestMapper.map(friendRequestDao.add(friendRequest));
    }

    @Override
    public void remove(Long id) {
        friendRequestDao.remove(id);
    }

    @Override
    public FriendRequestResponse accept(Long senderId) throws NotFoundException {
        User receiver = userDao.getByUsername(securityConfig.getLoggedInUsername());
        FriendRequest friendRequest = friendRequestDao.getBySenderIdAndReceiverId(senderId, receiver.getId());

        friendService.add(friendRequest.getSender().getId(), friendRequest.getReceiver().getId());
        remove(friendRequest.getId());

        return friendRequestMapper.map(friendRequest);
    }

    @Override
    public PageResponse<ShortFriendRequestResponse> getAll(Integer page, Integer limit) {
        return friendRequestMapper.map(friendRequestDao.getAll(page, limit));
    }

    private boolean exists(Long senderId, Long receiverId) {
        return friendRequestDao.existsBySenderIdAndReceiverId(senderId, receiverId) ||
               friendRequestDao.existsBySenderIdAndReceiverId(receiverId, senderId);
    }

    private void checkUser(Long id, Long receiverId) throws IllegalOperationException {
        if (id.equals(receiverId))
            throw new IllegalOperationException(messageSource.getMessage("friendRequest.sameUser", null,
                    LocaleContextHolder.getLocale()));
    }

    private void checkExist(Long senderId, Long receiverId) throws IllegalOperationException {
        if (friendService.isFriend(senderId, receiverId))
            throw new IllegalOperationException(messageSource.getMessage("friend.exist", null,
                    LocaleContextHolder.getLocale()));
        if (exists(senderId, receiverId))
            throw new IllegalOperationException(messageSource.getMessage("friendRequest.exist", null,
                    LocaleContextHolder.getLocale()));
    }
}
