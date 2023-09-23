package com.example.socialmedia.controller;

import com.example.socialmedia.dto.response.BaseResponse;
import com.example.socialmedia.dto.response.PageResponse;
import com.example.socialmedia.dto.response.ShortFriendResponse;
import com.example.socialmedia.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;
    private final MessageSource messageSource;

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long id) {
        friendService.remove(id);

        BaseResponse<Void> baseResponse = BaseResponse.<Void>builder().successMessage(messageSource
                .getMessage("friend.deleted", null, LocaleContextHolder.getLocale())).build();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(baseResponse);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<PageResponse<ShortFriendResponse>> getAll(@PathVariable Long userId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit) {
        PageResponse<ShortFriendResponse> pageResponse = friendService.getAll(userId, page, limit);

        return ResponseEntity.ok(pageResponse);
    }
}
