package com.example.socialmedia.controller;

import com.example.socialmedia.dto.response.BaseResponse;
import com.example.socialmedia.dto.response.FriendRequestResponse;
import com.example.socialmedia.dto.response.PageResponse;
import com.example.socialmedia.dto.response.ShortFriendRequestResponse;
import com.example.socialmedia.entity.FriendRequest;
import com.example.socialmedia.exception.NotFoundException;
import com.example.socialmedia.mapper.FriendRequestMapper;
import com.example.socialmedia.service.FriendRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friend-request")
@RequiredArgsConstructor
public class FriendRequestController {
    private final FriendRequestService friendRequestService;
    private final FriendRequestMapper friendRequestMapper;
    private final MessageSource messageSource;

    @PostMapping("/{userId}")
    public ResponseEntity<BaseResponse<FriendRequestResponse>> add(@PathVariable Long userId)
            throws NotFoundException {
        FriendRequest friendRequest = friendRequestService.add(userId);
        FriendRequestResponse response = friendRequestMapper.map(friendRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long id) {
        friendRequestService.remove(id);

        BaseResponse<Void> baseResponse = BaseResponse.<Void>builder().successMessage(messageSource
                .getMessage("friendRequest.deleted", null, LocaleContextHolder.getLocale())).build();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(baseResponse);
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity<BaseResponse<FriendRequestResponse>> accept(@PathVariable Long id) throws NotFoundException {
        FriendRequest friendRequest = friendRequestService.accept(id);
        FriendRequestResponse response = friendRequestMapper.map(friendRequest);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(BaseResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<PageResponse<ShortFriendRequestResponse>> getAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit) {
        Page<FriendRequest> friendRequests = friendRequestService.getAll(page, limit);
        PageResponse<ShortFriendRequestResponse> pageResponse = friendRequestMapper.map(friendRequests);

        return ResponseEntity.ok(pageResponse);
    }
}
