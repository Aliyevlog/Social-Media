package com.example.socialmedia.dao;

import com.example.socialmedia.entity.User;
import com.example.socialmedia.exception.NotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserDao {
    User register(User user);

    User update(User user);

    User getByUsername(String username) throws NotFoundException;

    User getByEmail(String email) throws NotFoundException;

    User getById(Long id) throws NotFoundException;

    List<User> getByName(String name);

    Page<User> getAll(String username, Integer page, Integer limit);

    void remove (Long id);
}
