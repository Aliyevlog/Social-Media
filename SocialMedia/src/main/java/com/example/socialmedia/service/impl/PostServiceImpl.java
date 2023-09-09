package com.example.socialmedia.service.impl;

import com.example.socialmedia.entity.Post;
import com.example.socialmedia.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    @Override
    public Post getByPostId(Long id) {
        return null;
    }
}
