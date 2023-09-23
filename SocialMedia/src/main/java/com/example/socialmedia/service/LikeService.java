package com.example.socialmedia.service;

import com.example.socialmedia.dto.response.LikeResponse;
import com.example.socialmedia.dto.response.PageResponse;
import com.example.socialmedia.dto.response.ShortLikeResponse;
import com.example.socialmedia.exception.NotFoundException;

public interface LikeService {
    LikeResponse like(Long postId) throws NotFoundException;

    LikeResponse dislike(Long postId) throws NotFoundException;

    void remove(Long postId) throws NotFoundException;

    Long countLikeByPost(Long postId);

    Long countDislikeByPost(Long postId);

    PageResponse<ShortLikeResponse> getByPostIdAndReaction(Long postId, Boolean reaction, Integer page, Integer limit);
}
