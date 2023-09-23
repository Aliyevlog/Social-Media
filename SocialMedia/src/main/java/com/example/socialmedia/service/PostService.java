package com.example.socialmedia.service;

import com.example.socialmedia.dto.request.CreatePostRequest;
import com.example.socialmedia.dto.request.UpdatePostRequest;
import com.example.socialmedia.dto.response.PageResponse;
import com.example.socialmedia.dto.response.PostResponse;
import com.example.socialmedia.exception.IllegalOperationException;
import com.example.socialmedia.exception.NotFoundException;

public interface PostService {
    PostResponse getByPostId(Long id) throws NotFoundException;

    PostResponse add(CreatePostRequest createPost) throws NotFoundException;

    PostResponse update(Long id, UpdatePostRequest updatePost) throws IllegalOperationException, NotFoundException;

    void remove(Long id) throws NotFoundException, IllegalOperationException;

    PageResponse<PostResponse> getAll(String text, String userName, Integer page, Integer limit);
}
