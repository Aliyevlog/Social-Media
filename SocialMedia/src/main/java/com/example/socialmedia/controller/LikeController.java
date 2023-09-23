package com.example.socialmedia.controller;

import com.example.socialmedia.dto.response.BaseResponse;
import com.example.socialmedia.dto.response.LikeResponse;
import com.example.socialmedia.dto.response.PageResponse;
import com.example.socialmedia.dto.response.ShortLikeResponse;
import com.example.socialmedia.exception.NotFoundException;
import com.example.socialmedia.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;
    private final MessageSource messageSource;

    @PostMapping("/like/{postId}")
    public ResponseEntity<BaseResponse<LikeResponse>> like(@PathVariable Long postId) throws NotFoundException {
        LikeResponse likeResponse = likeService.like(postId);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(BaseResponse.success(likeResponse));
    }

    @PostMapping("/dislike/{postId}")
    public ResponseEntity<BaseResponse<LikeResponse>> dislike(@PathVariable Long postId) throws NotFoundException {
        LikeResponse likeResponse = likeService.dislike(postId);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(BaseResponse.success(likeResponse));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long postId) throws NotFoundException {
        likeService.remove(postId);

        BaseResponse<Void> baseResponse = BaseResponse.<Void>builder().successMessage(messageSource
                .getMessage("like.deleted", null, LocaleContextHolder.getLocale())).build();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(baseResponse);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PageResponse<ShortLikeResponse>> filter(
            @PathVariable Long postId,
            @RequestParam(required = false) Boolean like,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit) {
        PageResponse<ShortLikeResponse> pageResponse = likeService.getByPostIdAndReaction(postId, like, page, limit);

        return ResponseEntity.ok(pageResponse);
    }
}
