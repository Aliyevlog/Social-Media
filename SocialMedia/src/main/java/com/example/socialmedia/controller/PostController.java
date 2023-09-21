package com.example.socialmedia.controller;

import com.example.socialmedia.config.SecurityConfig;
import com.example.socialmedia.dto.request.CreatePostRequest;
import com.example.socialmedia.dto.request.UpdatePostRequest;
import com.example.socialmedia.dto.response.BaseResponse;
import com.example.socialmedia.dto.response.PageResponse;
import com.example.socialmedia.dto.response.PostResponse;
import com.example.socialmedia.entity.Post;
import com.example.socialmedia.entity.User;
import com.example.socialmedia.exception.IllegalOperationException;
import com.example.socialmedia.exception.NotFoundException;
import com.example.socialmedia.mapper.PostMapper;
import com.example.socialmedia.service.PostService;
import com.example.socialmedia.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final SecurityConfig securityConfig;
    private final UserService userService;
    private final MessageSource messageSource;

    @GetMapping("/getById/{id}")
    public ResponseEntity<BaseResponse<PostResponse>> getPostById(@PathVariable Long id)
            throws NotFoundException {

        Post post = postService.getByPostId(id);
        PostResponse postResponse = postMapper.map(post);

        return ResponseEntity.ok(BaseResponse.success(postResponse));
    }

    @PostMapping
    public ResponseEntity<BaseResponse<PostResponse>> add(@RequestBody @Valid CreatePostRequest request)
            throws NotFoundException {
        User user = userService.getByUsername(securityConfig.getLoggedInUsername());
        Post post = postMapper.map(request);
        post.setUser(user);
        post = postService.add(post);
        PostResponse postResponse = postMapper.map(post);

        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(postResponse));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BaseResponse<PostResponse>> update(@PathVariable Long id,
            @RequestBody @Valid UpdatePostRequest request)
            throws NotFoundException, IllegalOperationException {
        Post post = postService.getByPostId(id);
        postMapper.map(request, post);
        post = postService.update(post);
        PostResponse postResponse = postMapper.map(post);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(BaseResponse.success(postResponse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long id) throws NotFoundException, IllegalOperationException {
        postService.remove(id);

        BaseResponse<Void> baseResponse = BaseResponse.<Void>builder().successMessage(messageSource
                .getMessage("post.deleted", null, LocaleContextHolder.getLocale())).build();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(baseResponse);

    }

    @GetMapping
    public ResponseEntity<PageResponse<PostResponse>> getAll(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit) {
        Page<Post> postPage = postService.getAll(text, name, page, limit);
        PageResponse<PostResponse> pageResponse = postMapper.map(postPage);

        return ResponseEntity.ok(pageResponse);
    }
}
