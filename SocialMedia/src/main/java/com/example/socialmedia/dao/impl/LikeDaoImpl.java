package com.example.socialmedia.dao.impl;

import com.example.socialmedia.dao.LikeDao;
import com.example.socialmedia.entity.Like;
import com.example.socialmedia.exception.NotFoundException;
import com.example.socialmedia.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeDaoImpl implements LikeDao {
    private final LikeRepository likeRepository;

    @Override
    public Like add(Like like) {
        return likeRepository.save(like);
    }

    @Override
    public Like update(Like like) {
        return likeRepository.save(like);
    }

    @Override
    public void remove(Long id) {
        likeRepository.deleteById(id);
    }

    @Override
    public Long countByPostIdAndReaction(Long postId, boolean reaction) {
        return likeRepository.countByPostIdAndReaction(postId, reaction);
    }

    @Override
    public Page<Like> getByPostIdAndReaction(Long postId, Boolean reaction, Integer page, Integer limit) {
        Pageable pageable = page != null && limit != null ? PageRequest.of(page - 1, limit) :
                PageRequest.of(0, 10);

        return likeRepository.findLikeByPostIdAndReaction(postId, reaction, pageable);
    }

    @Override
    public Like getByUserIdAndPostId(Long userId, Long postId) throws NotFoundException {
        return likeRepository.findByUserIdAndPostId(userId, postId).orElseThrow(NotFoundException::new);
    }
}
