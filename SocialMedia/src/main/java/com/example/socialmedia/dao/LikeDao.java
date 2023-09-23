package com.example.socialmedia.dao;

import com.example.socialmedia.entity.Like;
import com.example.socialmedia.exception.NotFoundException;
import org.springframework.data.domain.Page;

public interface LikeDao {
    Like add(Like like);

    Like update(Like like);

    void remove(Long id);

    Long countByPostIdAndReaction(Long postId, boolean reaction);

    Page<Like> getByPostIdAndReaction(Long postId, Boolean reaction, Integer page, Integer limit);

    Like getByUserIdAndPostId(Long userId, Long postId) throws NotFoundException;
}
