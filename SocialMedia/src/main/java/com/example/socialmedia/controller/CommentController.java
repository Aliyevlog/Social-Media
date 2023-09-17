package com.example.socialmedia.controller;

import com.example.socialmedia.config.SecurityConfig;
import com.example.socialmedia.dto.request.CreateCommentRequest;
import com.example.socialmedia.dto.request.UpdateCommentRequest;
import com.example.socialmedia.dto.response.BaseResponse;
import com.example.socialmedia.dto.response.CommentResponse;
import com.example.socialmedia.dto.response.PageResponse;
import com.example.socialmedia.entity.Comment;
import com.example.socialmedia.entity.Post;
import com.example.socialmedia.entity.User;
import com.example.socialmedia.exception.IllegalOperationException;
import com.example.socialmedia.exception.NotFoundException;
import com.example.socialmedia.mapper.CommentMapper;
import com.example.socialmedia.service.CommentService;
import com.example.socialmedia.service.PostService;
import com.example.socialmedia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final PostService postService;
    private final UserService userService;
    private final SecurityConfig securityConfig;
    private final MessageSource messageSource;

    @PostMapping("/{postId}")
    public ResponseEntity<BaseResponse<CommentResponse>> add(@PathVariable Long postId,
                                                             @RequestBody CreateCommentRequest request)
            throws NotFoundException {
        Post post = postService.getByPostId(postId);
        User user = userService.getByUsername(securityConfig.getLoggedInUsername());
        Comment comment = commentMapper.map(request);
        comment.setUser(user);
        comment.setPost(post);

        comment = commentService.add(comment);
        CommentResponse commentResponse = commentMapper.map(comment);

        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(commentResponse));
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<BaseResponse<CommentResponse>> update(@PathVariable Long commentId,
                                                                @RequestBody UpdateCommentRequest request)
            throws NotFoundException, IllegalOperationException {
        Comment comment = commentService.getById(commentId);
        commentMapper.map(request, comment);
        comment = commentService.update(comment);
        CommentResponse commentResponse = commentMapper.map(comment);

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
        Post post = postService.getByPostId(postId);
        Page<Comment> commentPage = commentService.getByPost(post, page, limit);
        PageResponse<CommentResponse> pageResponse = commentMapper.map(commentPage);

        return ResponseEntity.ok(pageResponse);
    }
}
