package com.example.socialmedia.service;

import com.example.socialmedia.entity.User;
import com.example.socialmedia.exception.NotFoundException;

public interface UserService
{
    User register (User user);

    User getByUsername (String username) throws NotFoundException;
}
