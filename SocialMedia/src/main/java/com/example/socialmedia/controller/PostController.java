package com.example.socialmedia.controller;

import com.example.socialmedia.config.SecurityConfig;
import com.example.socialmedia.dto.response.BaseResponse;
import com.example.socialmedia.dto.response.PostResponse;
import com.example.socialmedia.dto.response.UserResponse;
import com.example.socialmedia.entity.Post;
import com.example.socialmedia.exception.NotFoundException;
import com.example.socialmedia.mapper.PostMapper;
import com.example.socialmedia.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final SecurityConfig securityConfig;

    @GetMapping("/getById/{id}")
    public ResponseEntity<BaseResponse<UserResponse>> getPostById(@PathVariable Long id) throws NotFoundException {

        Post post = postService.getByPostId(id);
        PostResponse postResponse = postMapper.map(post);

        return ResponseEntity.ok(BaseResponse.success(postResponse));
    }


}
