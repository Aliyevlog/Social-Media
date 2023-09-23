package com.example.socialmedia.controller;

import com.example.socialmedia.dto.response.BaseResponse;
import com.example.socialmedia.dto.response.FriendRequestResponse;
import com.example.socialmedia.dto.response.PageResponse;
import com.example.socialmedia.dto.response.ShortFriendRequestResponse;
import com.example.socialmedia.exception.IllegalOperationException;
import com.example.socialmedia.exception.NotFoundException;
import com.example.socialmedia.service.FriendRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friend-request")
@RequiredArgsConstructor
public class FriendRequestController {
    private final FriendRequestService friendRequestService;
    private final MessageSource messageSource;

    @PostMapping("/{userId}")
    public ResponseEntity<BaseResponse<FriendRequestResponse>> add(@PathVariable Long userId)
            throws NotFoundException, IllegalOperationException {
        FriendRequestResponse response = friendRequestService.add(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long id) {
        friendRequestService.remove(id);

        BaseResponse<Void> baseResponse = BaseResponse.<Void>builder().successMessage(messageSource
                .getMessage("friendRequest.deleted", null, LocaleContextHolder.getLocale())).build();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(baseResponse);
    }

    @PostMapping("/accept/{senderId}")
    public ResponseEntity<BaseResponse<FriendRequestResponse>> accept(@PathVariable Long senderId) throws NotFoundException {
        FriendRequestResponse response = friendRequestService.accept(senderId);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(BaseResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<PageResponse<ShortFriendRequestResponse>> getAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit) {
        PageResponse<ShortFriendRequestResponse> pageResponse = friendRequestService.getAll(page, limit);

        return ResponseEntity.ok(pageResponse);
    }
}
