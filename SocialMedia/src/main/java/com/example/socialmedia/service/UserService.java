package com.example.socialmedia.service;

import com.example.socialmedia.entity.User;
import com.example.socialmedia.exception.AlreadyExistException;
import com.example.socialmedia.exception.NotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService
{
    User register (User user) throws AlreadyExistException;
    User update (User user) throws AlreadyExistException;

    User getByUsername (String username) throws NotFoundException;

    User getById (Long id) throws NotFoundException;

    List<User> getByName (String name);

    Page<User> getAll (String username, Integer page, Integer limit);
}
