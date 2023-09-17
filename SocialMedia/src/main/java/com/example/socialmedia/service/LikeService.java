package com.example.socialmedia.service;

import com.example.socialmedia.entity.Like;
import com.example.socialmedia.exception.NotFoundException;
import org.springframework.data.domain.Page;

public interface LikeService {
    Like like(Long postId) throws NotFoundException;

    Like dislike(Long postId) throws NotFoundException;

    void remove(Long postId) throws NotFoundException;

    Long countLikeByPost(Long postId);

    Long countDislikeByPost(Long postId);

    Page<Like> getByPostIdAndReaction(Long postId, Boolean reaction, Integer page, Integer limit);
}
