package com.example.socialmedia.service.impl;

import com.example.socialmedia.config.SecurityConfig;
import com.example.socialmedia.entity.Comment;
import com.example.socialmedia.entity.Post;
import com.example.socialmedia.exception.IllegalOperationException;
import com.example.socialmedia.exception.NotFoundException;
import com.example.socialmedia.repository.CommentRepository;
import com.example.socialmedia.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final MessageSource messageSource;
    private final SecurityConfig securityConfig;

    @Override
    public Comment add(Comment comment) {
        comment.setCreatedAt(new Date());

        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Comment comment) throws IllegalOperationException {
        if (!comment.getUser().getUsername().equals(securityConfig.getLoggedInUsername()))
            throw new IllegalOperationException(messageSource.getMessage("comment.illegalUpdate", null,
                    LocaleContextHolder.getLocale()));

        return commentRepository.save(comment);
    }

    @Override
    public Page<Comment> getByPost(Post post, Integer page, Integer limit) {
        Pageable pageable = page != null && limit != null ?
                PageRequest.of(page - 1, limit) :
                PageRequest.of(0, 10);

        return commentRepository.findByPost(post, pageable);
    }

    @Override
    public Comment getById(Long id) throws NotFoundException {
        return commentRepository.findById(id).orElseThrow(() -> new NotFoundException(messageSource
                .getMessage("comment.notFoundById", null, LocaleContextHolder.getLocale())));
    }

    @Override
    public void remove(Long id) throws NotFoundException, IllegalOperationException {
        Comment comment = getById(id);
        if (!comment.getUser().getUsername().equals(securityConfig.getLoggedInUsername()))
            throw new IllegalOperationException(messageSource.getMessage("comment.illegalDelete", null,
                    LocaleContextHolder.getLocale()));

        commentRepository.deleteById(id);
    }
}
