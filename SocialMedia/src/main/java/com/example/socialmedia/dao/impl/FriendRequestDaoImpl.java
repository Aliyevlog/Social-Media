package com.example.socialmedia.dao.impl;

import com.example.socialmedia.config.SecurityConfig;
import com.example.socialmedia.dao.FriendRequestDao;
import com.example.socialmedia.entity.FriendRequest;
import com.example.socialmedia.exception.NotFoundException;
import com.example.socialmedia.repository.FriendRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendRequestDaoImpl implements FriendRequestDao {
    private final FriendRequestRepository friendRequestRepository;
    private final SecurityConfig securityConfig;
    private final MessageSource messageSource;

    @Override
    public FriendRequest add(FriendRequest request) {
        return friendRequestRepository.save(request);
    }

    @Override
    public void remove(Long id) {
        friendRequestRepository.deleteById(id);
    }

    @Override
    public Page<FriendRequest> getAll(Integer page, Integer limit) {
        Pageable pageable = page != null && limit != null ? PageRequest.of(page - 1, limit) :
                PageRequest.of(0, 10);

        return friendRequestRepository.findByReceiverUsername(securityConfig.getLoggedInUsername(), pageable);
    }

    @Override
    public boolean existsBySenderIdAndReceiverId(Long senderId, Long receiverId) {
        return friendRequestRepository.existsBySenderIdAndReceiverId(senderId, receiverId);
    }

    @Override
    public FriendRequest getBySenderIdAndReceiverId(Long senderId, Long receiverId) throws NotFoundException {
        return friendRequestRepository.findBySenderIdAndReceiverId(senderId, receiverId).orElseThrow(() -> new NotFoundException(messageSource
                .getMessage("friendRequest.notFoundById", null, LocaleContextHolder.getLocale())));
    }

    @Override
    public void removeByUserId(Long userId) {
        friendRequestRepository.deleteAllBySenderIdOrReceiverId(userId, userId);
    }
}
