package com.example.socialmedia.service.impl;

import com.example.socialmedia.config.SecurityConfig;
import com.example.socialmedia.dao.CommentDao;
import com.example.socialmedia.dao.PostDao;
import com.example.socialmedia.dto.request.CreateCommentRequest;
import com.example.socialmedia.dto.request.UpdateCommentRequest;
import com.example.socialmedia.dto.response.CommentResponse;
import com.example.socialmedia.dto.response.PageResponse;
import com.example.socialmedia.entity.Comment;
import com.example.socialmedia.entity.Post;
import com.example.socialmedia.exception.IllegalOperationException;
import com.example.socialmedia.exception.NotFoundException;
import com.example.socialmedia.mapper.CommentMapper;
import com.example.socialmedia.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentDao commentDao;
    private final MessageSource messageSource;
    private final SecurityConfig securityConfig;
    private final CommentMapper commentMapper;
    private final PostDao postDao;

    @Override
    public CommentResponse add(Long postId, CreateCommentRequest createComment) throws NotFoundException {
        Comment comment = commentMapper.map(createComment, postId);

        return commentMapper.map(commentDao.add(comment));
    }

    @Override
    public CommentResponse update(Long commentId, UpdateCommentRequest updateComment)
            throws IllegalOperationException, NotFoundException {
        Comment comment = commentDao.getById(commentId);
        commentMapper.map(updateComment, comment);

        if (!comment.getUser().getUsername().equals(securityConfig.getLoggedInUsername()))
            throw new IllegalOperationException(messageSource.getMessage("comment.illegalUpdate", null,
                    LocaleContextHolder.getLocale()));

        return commentMapper.map(commentDao.add(comment));
    }

    @Override
    public PageResponse<CommentResponse> getByPost(Long postId, Integer page, Integer limit) throws NotFoundException {
        Post post = postDao.getByPostId(postId);
        Page<Comment> commentPage = commentDao.getByPost(post, page, limit);

        return commentMapper.map(commentPage);
    }

    @Override
    public void remove(Long id) throws NotFoundException, IllegalOperationException {
        Comment comment = commentDao.getById(id);
        if (!comment.getUser().getUsername().equals(securityConfig.getLoggedInUsername()))
            throw new IllegalOperationException(messageSource.getMessage("comment.illegalDelete", null,
                    LocaleContextHolder.getLocale()));

        commentDao.remove(id);
    }
}
