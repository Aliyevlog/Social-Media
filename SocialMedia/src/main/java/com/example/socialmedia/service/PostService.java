package com.example.socialmedia.service;

import com.example.socialmedia.entity.Post;
import com.example.socialmedia.exception.IllegalOperationException;
import com.example.socialmedia.exception.NotFoundException;
import org.springframework.data.domain.Page;

public interface PostService {
    Post getByPostId(Long id) throws NotFoundException;

    Post add(Post post);

    Post update(Post post) throws IllegalOperationException;

    void remove(Long id) throws NotFoundException, IllegalOperationException;

    Page<Post> getAll (String text, String userName, Integer page, Integer limit);
}
