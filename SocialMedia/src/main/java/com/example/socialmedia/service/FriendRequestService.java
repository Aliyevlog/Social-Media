package com.example.socialmedia.service;

import com.example.socialmedia.dto.response.FriendRequestResponse;
import com.example.socialmedia.dto.response.PageResponse;
import com.example.socialmedia.dto.response.ShortFriendRequestResponse;
import com.example.socialmedia.exception.IllegalOperationException;
import com.example.socialmedia.exception.NotFoundException;

public interface FriendRequestService {
    FriendRequestResponse add(Long receiverId) throws NotFoundException, IllegalOperationException;

    void remove(Long id);

    FriendRequestResponse accept(Long senderId) throws NotFoundException;

    PageResponse<ShortFriendRequestResponse> getAll(Integer page, Integer limit);
}
