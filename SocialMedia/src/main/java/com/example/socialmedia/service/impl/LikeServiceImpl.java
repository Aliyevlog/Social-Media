package com.example.socialmedia.service.impl;

import com.example.socialmedia.config.SecurityConfig;
import com.example.socialmedia.entity.Like;
import com.example.socialmedia.entity.User;
import com.example.socialmedia.exception.NotFoundException;
import com.example.socialmedia.repository.LikeRepository;
import com.example.socialmedia.service.LikeService;
import com.example.socialmedia.service.PostService;
import com.example.socialmedia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final UserService userService;
    private final PostService postService;
    private final SecurityConfig securityConfig;

    @Override
    public Like like(Long postId) throws NotFoundException {
        return changeReaction(getUserId(), postId, true);
    }

    @Override
    public Like dislike(Long postId) throws NotFoundException {
        return changeReaction(getUserId(), postId, false);
    }

    @Override
    public void remove(Long postId) throws NotFoundException {
        likeRepository.findByUserIdAndPostId(getUserId(), postId).ifPresent(likeRepository::delete);
    }

    @Override
    public Long countLikeByPost(Long postId) {
        return likeRepository.countByPostIdAndReaction(postId, true);
    }

    @Override
    public Long countDislikeByPost(Long postId) {
        return likeRepository.countByPostIdAndReaction(postId, false);
    }

    @Override
    public Page<Like> getByPostIdAndReaction(Long postId, Boolean reaction, Integer page, Integer limit) {
        Pageable pageable = page != null && limit != null ? PageRequest.of(page - 1, limit) :
                PageRequest.of(0, 10);

        return likeRepository.findLikeByPostIdAndReaction(postId, reaction, pageable);
    }

    private Like changeReaction(Long userId, Long postId, Boolean reaction) throws NotFoundException {
        Like like = likeRepository.findByUserIdAndPostId(userId, postId).orElse(null);
        if (like != null) {
            like.setReaction(reaction);
            like = likeRepository.save(like);
            return like;
        }

        like = Like.builder()
                .user(userService.getById(userId))
                .post(postService.getByPostId(postId))
                .reaction(reaction)
                .build();
        like = likeRepository.save(like);
        return like;
    }

    private Long getUserId() throws NotFoundException {
        String username = securityConfig.getLoggedInUsername();
        User user = userService.getByUsername(username);
        return user.getId();
    }
}
