package com.example.socialmedia.controller;

import com.example.socialmedia.dto.request.CreatePostRequest;
import com.example.socialmedia.dto.request.UpdatePostRequest;
import com.example.socialmedia.dto.response.BaseResponse;
import com.example.socialmedia.dto.response.PageResponse;
import com.example.socialmedia.dto.response.PostResponse;
import com.example.socialmedia.exception.IllegalOperationException;
import com.example.socialmedia.exception.NotFoundException;
import com.example.socialmedia.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final MessageSource messageSource;

    @GetMapping("/getById/{id}")
    public ResponseEntity<BaseResponse<PostResponse>> getPostById(@PathVariable Long id)
            throws NotFoundException {
        PostResponse postResponse = postService.getByPostId(id);

        return ResponseEntity.ok(BaseResponse.success(postResponse));
    }

    @PostMapping
    public ResponseEntity<BaseResponse<PostResponse>> add(@RequestBody @Valid CreatePostRequest request)
            throws NotFoundException {
        PostResponse postResponse = postService.add(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(postResponse));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BaseResponse<PostResponse>> update(@PathVariable Long id,
            @RequestBody @Valid UpdatePostRequest request)
            throws NotFoundException, IllegalOperationException {
        PostResponse postResponse = postService.update(id, request);

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
        PageResponse<PostResponse> pageResponse = postService.getAll(text, name, page, limit);

        return ResponseEntity.ok(pageResponse);
    }
}
