package com.example.socialmedia.service;

import com.example.socialmedia.dto.request.CreateCommentRequest;
import com.example.socialmedia.dto.request.UpdateCommentRequest;
import com.example.socialmedia.dto.response.CommentResponse;
import com.example.socialmedia.dto.response.PageResponse;
import com.example.socialmedia.exception.IllegalOperationException;
import com.example.socialmedia.exception.NotFoundException;

public interface CommentService {
    CommentResponse add(Long postId, CreateCommentRequest createComment) throws NotFoundException;

    CommentResponse update(Long commentId, UpdateCommentRequest updateComment) throws IllegalOperationException, NotFoundException;

    PageResponse<CommentResponse> getByPost(Long postId, Integer page, Integer limit) throws NotFoundException;

    void remove(Long id) throws NotFoundException, IllegalOperationException;
}
