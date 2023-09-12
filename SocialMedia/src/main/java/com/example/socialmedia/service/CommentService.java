package com.example.socialmedia.service;

import com.example.socialmedia.entity.Comment;
import com.example.socialmedia.entity.Post;
import com.example.socialmedia.exception.IllegalOperationException;
import com.example.socialmedia.exception.NotFoundException;
import org.springframework.data.domain.Page;

public interface CommentService {
    Comment add(Comment comment);

    Comment update(Comment comment) throws IllegalOperationException;

    Page<Comment> getByPost(Post post, Integer page, Integer limit);

    Comment getById (Long id) throws NotFoundException;

    void remove(Long id) throws NotFoundException, IllegalOperationException;
}
