package com.example.socialmedia.dao;

import com.example.socialmedia.entity.Post;
import com.example.socialmedia.exception.NotFoundException;
import org.springframework.data.domain.Page;

public interface PostDao {
    Post getByPostId(Long id) throws NotFoundException;

    Post add(Post post);

    Post update(Post post);

    void remove(Long id);

    void removeByUserId (Long userId);
    Page<Post> getAll(String text, String userName, Integer page, Integer limit);
}
