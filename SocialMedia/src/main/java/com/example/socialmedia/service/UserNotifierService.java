package com.example.socialmedia.service;

import com.example.socialmedia.entity.User;

import java.io.IOException;

public interface UserNotifierService {
    void notifyRegistered(User user) throws IOException;
}
