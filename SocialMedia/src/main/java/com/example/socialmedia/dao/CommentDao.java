package com.example.socialmedia.dao;

import com.example.socialmedia.entity.Comment;
import com.example.socialmedia.entity.Post;
import com.example.socialmedia.exception.NotFoundException;
import org.springframework.data.domain.Page;

public interface CommentDao {
    Comment add(Comment comment);

    Comment update(Comment comment);

    Page<Comment> getByPost(Post post, Integer page, Integer limit);

    Comment getById(Long id) throws NotFoundException;

    void remove(Long id);
}
