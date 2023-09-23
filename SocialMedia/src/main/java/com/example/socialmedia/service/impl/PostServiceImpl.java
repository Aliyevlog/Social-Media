package com.example.socialmedia.service.impl;

import com.example.socialmedia.config.SecurityConfig;
import com.example.socialmedia.dao.PostDao;
import com.example.socialmedia.dto.request.CreatePostRequest;
import com.example.socialmedia.dto.request.UpdatePostRequest;
import com.example.socialmedia.dto.response.PageResponse;
import com.example.socialmedia.dto.response.PostResponse;
import com.example.socialmedia.entity.Post;
import com.example.socialmedia.exception.IllegalOperationException;
import com.example.socialmedia.exception.NotFoundException;
import com.example.socialmedia.mapper.PostMapper;
import com.example.socialmedia.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostDao postDao;
    private final MessageSource messageSource;
    private final PostMapper postMapper;
    private final SecurityConfig securityConfig;

    @Override
    public PostResponse getByPostId(Long id) throws NotFoundException {
        return postMapper.map(postDao.getByPostId(id));
    }

    @Override
    public PostResponse add(CreatePostRequest createPost) throws NotFoundException {
        Post post = postMapper.map(createPost);

        return postMapper.map(postDao.add(post));
    }

    @Override
    public PostResponse update(Long id, UpdatePostRequest updatePost)
            throws IllegalOperationException, NotFoundException {
        Post post = postDao.getByPostId(id);
        postMapper.map(updatePost, post);

        if (!post.getUser().getUsername().equals(securityConfig.getLoggedInUsername()))
            throw new IllegalOperationException(messageSource.getMessage("post.illegalUpdate", null,
                    LocaleContextHolder.getLocale()));
        return postMapper.map(postDao.update(post));
    }

    @Override
    public void remove(Long id) throws NotFoundException, IllegalOperationException {
        Post post = postDao.getByPostId(id);
        if (!post.getUser().getUsername().equals(securityConfig.getLoggedInUsername()))
            throw new IllegalOperationException(messageSource.getMessage("post.illegalDelete", null,
                    LocaleContextHolder.getLocale()));
        postDao.remove(id);
    }

    @Override
    public PageResponse<PostResponse> getAll(String text, String userName, Integer page, Integer limit) {
        return postMapper.map(postDao.getAll(text, userName, page, limit));
    }
}
