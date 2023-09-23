package com.example.socialmedia.service.impl;

import com.example.socialmedia.config.SecurityConfig;
import com.example.socialmedia.dao.LikeDao;
import com.example.socialmedia.dao.PostDao;
import com.example.socialmedia.dao.UserDao;
import com.example.socialmedia.dto.response.LikeResponse;
import com.example.socialmedia.dto.response.PageResponse;
import com.example.socialmedia.dto.response.ShortLikeResponse;
import com.example.socialmedia.entity.Like;
import com.example.socialmedia.entity.User;
import com.example.socialmedia.exception.NotFoundException;
import com.example.socialmedia.mapper.LikeMapper;
import com.example.socialmedia.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeDao likeDao;
    private final UserDao userDao;
    private final PostDao postDao;
    private final SecurityConfig securityConfig;
    private final LikeMapper likeMapper;

    @Override
    public LikeResponse like(Long postId) throws NotFoundException {
        return likeMapper.map(changeReaction(getUserId(), postId, true));
    }

    @Override
    public LikeResponse dislike(Long postId) throws NotFoundException {
        return likeMapper.map(changeReaction(getUserId(), postId, false));
    }

    @Override
    public void remove(Long postId) throws NotFoundException {
        Like like = likeDao.getByUserIdAndPostId(getUserId(), postId);
        likeDao.remove(like.getId());
    }

    @Override
    public Long countLikeByPost(Long postId) {
        return likeDao.countByPostIdAndReaction(postId, true);
    }

    @Override
    public Long countDislikeByPost(Long postId) {
        return likeDao.countByPostIdAndReaction(postId, false);
    }

    @Override
    public PageResponse<ShortLikeResponse> getByPostIdAndReaction(Long postId, Boolean reaction, Integer page, Integer limit) {
        return likeMapper.map(likeDao.getByPostIdAndReaction(postId, reaction, page, limit));
    }

    private Like changeReaction(Long userId, Long postId, Boolean reaction) throws NotFoundException {
        Like like;
        try {
            like = likeDao.getByUserIdAndPostId(userId, postId);
            like.setReaction(reaction);
            like = likeDao.update(like);
            return like;
        } catch (NotFoundException ex) {
            like = Like.builder()
                    .user(userDao.getById(userId))
                    .post(postDao.getByPostId(postId))
                    .reaction(reaction)
                    .build();
            like = likeDao.add(like);
        }

        return like;
    }

    private Long getUserId() throws NotFoundException {
        String username = securityConfig.getLoggedInUsername();
        User user = userDao.getByUsername(username);
        return user.getId();
    }
}
