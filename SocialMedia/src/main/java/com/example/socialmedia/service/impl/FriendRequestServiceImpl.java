package com.example.socialmedia.service.impl;

import com.example.socialmedia.config.SecurityConfig;
import com.example.socialmedia.entity.FriendRequest;
import com.example.socialmedia.entity.User;
import com.example.socialmedia.exception.IllegalOperationException;
import com.example.socialmedia.exception.NotFoundException;
import com.example.socialmedia.repository.FriendRequestRepository;
import com.example.socialmedia.service.FriendRequestService;
import com.example.socialmedia.service.FriendService;
import com.example.socialmedia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendRequestServiceImpl implements FriendRequestService {
    private final FriendRequestRepository friendRequestRepository;
    private final UserService userService;
    private final MessageSource messageSource;
    private final FriendService friendService;
    private final SecurityConfig securityConfig;

    @Override
    public FriendRequest add(Long receiverId) throws NotFoundException, IllegalOperationException {
        User sender = userService.getByUsername(securityConfig.getLoggedInUsername());
        if (friendService.isFriend(sender.getId(), receiverId))
            throw new IllegalOperationException(messageSource.getMessage("friend.exist", null,
                    LocaleContextHolder.getLocale()));
        if (exists(sender.getId(), receiverId))
            throw new IllegalOperationException(messageSource.getMessage("friendRequest.exist", null,
                    LocaleContextHolder.getLocale()));

        FriendRequest friendRequest = FriendRequest.builder()
                .sender(sender)
                .receiver(userService.getById(receiverId))
                .build();

        return friendRequestRepository.save(friendRequest);
    }

    @Override
    public void remove(Long id) {
        friendRequestRepository.deleteById(id);
    }

    @Override
    public FriendRequest accept(Long senderId) throws NotFoundException {
        User receiver = userService.getByUsername(securityConfig.getLoggedInUsername());
        FriendRequest friendRequest = friendRequestRepository.findBySenderIdAndReceiverId(senderId, receiver.getId())
                .orElseThrow(() -> new NotFoundException(
                        messageSource.getMessage("friendRequest.notFoundById", null, LocaleContextHolder.getLocale())));

        friendService.add(friendRequest.getSender().getId(), friendRequest.getReceiver().getId());
        remove(friendRequest.getId());

        return friendRequest;
    }

    @Override
    public Page<FriendRequest> getAll(Integer page, Integer limit) {
        Pageable pageable = page != null && limit != null ? PageRequest.of(page - 1, limit) :
                PageRequest.of(0, 10);

        return friendRequestRepository.findByReceiverUsername(securityConfig.getLoggedInUsername(), pageable);
    }

    private boolean exists(Long senderId, Long receiverId) {
        return friendRequestRepository.existsBySenderIdAndReceiverId(senderId, receiverId) ||
               friendRequestRepository.existsBySenderIdAndReceiverId(receiverId, senderId);
    }
}
