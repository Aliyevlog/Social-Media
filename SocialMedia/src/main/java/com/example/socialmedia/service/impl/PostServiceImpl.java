package com.example.socialmedia.service.impl;

import com.example.socialmedia.config.SecurityConfig;
import com.example.socialmedia.entity.Post;
import com.example.socialmedia.entity.User;
import com.example.socialmedia.exception.IllegalOperationException;
import com.example.socialmedia.exception.NotFoundException;
import com.example.socialmedia.repository.PostRepository;
import com.example.socialmedia.service.PostService;
import com.example.socialmedia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final MessageSource messageSource;
    private final UserService userService;
    private final SecurityConfig securityConfig;

    @Override
    public Post getByPostId(Long id) throws NotFoundException {
        return postRepository.findById(id).orElseThrow(() -> new NotFoundException(
                messageSource.getMessage("post.notFoundById", null, LocaleContextHolder.getLocale())));
    }

    @Override
    public Post add(Post post) {
        post.setCreatedAt(new Date());

        return postRepository.save(post);
    }

    @Override
    public Post update(Post post) throws IllegalOperationException {
        if (!post.getUser().getUsername().equals(securityConfig.getLoggedInUsername()))
            throw new IllegalOperationException(messageSource.getMessage("post.illegalUpdate", null,
                    LocaleContextHolder.getLocale()));
        return postRepository.save(post);
    }

    @Override
    public void remove(Long id) throws NotFoundException, IllegalOperationException {
        Post post = getByPostId(id);
        if (!post.getUser().getUsername().equals(securityConfig.getLoggedInUsername()))
            throw new IllegalOperationException(messageSource.getMessage("post.illegalDelete", null,
                    LocaleContextHolder.getLocale()));
        postRepository.deleteById(id);
    }

    @Override
    public Page<Post> getAll(String text, String userName, Integer page, Integer limit) {
        Pageable pageable = (page != null && limit != null) ?
                PageRequest.of(page - 1, limit) :
                PageRequest.of(0, 10);

        text = text == null ? "" : text;
        userName = userName == null ? "" : text;

        List<User> users = userService.getByName(userName);

        return postRepository.findByTextContainingAndUserIn(text, users, pageable);
    }
}
