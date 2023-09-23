package com.example.socialmedia.controller;

import com.example.socialmedia.dto.request.CreateCommentRequest;
import com.example.socialmedia.dto.request.UpdateCommentRequest;
import com.example.socialmedia.dto.response.BaseResponse;
import com.example.socialmedia.dto.response.CommentResponse;
import com.example.socialmedia.dto.response.PageResponse;
import com.example.socialmedia.exception.IllegalOperationException;
import com.example.socialmedia.exception.NotFoundException;
import com.example.socialmedia.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final MessageSource messageSource;

    @PostMapping("/{postId}")
    public ResponseEntity<BaseResponse<CommentResponse>> add(@PathVariable Long postId,
            @RequestBody @Valid CreateCommentRequest request)
            throws NotFoundException {
        CommentResponse commentResponse = commentService.add(postId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(commentResponse));
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<BaseResponse<CommentResponse>> update(@PathVariable Long commentId,
            @RequestBody @Valid UpdateCommentRequest request)
            throws NotFoundException, IllegalOperationException {
        CommentResponse commentResponse = commentService.update(commentId, request);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(BaseResponse.success(commentResponse));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long commentId)
            throws NotFoundException, IllegalOperationException {
        commentService.remove(commentId);

        BaseResponse<Void> baseResponse = BaseResponse.<Void>builder().successMessage(messageSource
                .getMessage("comment.deleted", null, LocaleContextHolder.getLocale())).build();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(baseResponse);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PageResponse<CommentResponse>> getByPost(@PathVariable Long postId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit)
            throws NotFoundException {
        PageResponse<CommentResponse> pageResponse = commentService.getByPost(postId, page, limit);

        return ResponseEntity.ok(pageResponse);
    }
}
