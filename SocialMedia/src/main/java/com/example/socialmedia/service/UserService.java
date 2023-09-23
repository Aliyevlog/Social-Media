package com.example.socialmedia.service;

import com.example.socialmedia.dto.request.UpdateUserRequest;
import com.example.socialmedia.dto.request.UserRegisterRequest;
import com.example.socialmedia.dto.response.FileResponse;
import com.example.socialmedia.dto.response.PageResponse;
import com.example.socialmedia.dto.response.UserResponse;
import com.example.socialmedia.exception.AlreadyExistException;
import com.example.socialmedia.exception.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    UserResponse register(UserRegisterRequest request) throws AlreadyExistException;

    UserResponse update(UpdateUserRequest updateUser) throws AlreadyExistException, NotFoundException;

    UserResponse getByUsername(String username) throws NotFoundException;

    UserResponse getLoggedInUser() throws NotFoundException;

    UserResponse getById(Long id) throws NotFoundException;

    PageResponse<UserResponse> getAll(String username, Integer page, Integer limit);

    UserResponse uploadProfilePhoto (MultipartFile multipartFile) throws IOException, NotFoundException;

    FileResponse getProfilePhoto (Long userId) throws NotFoundException, IOException;
}
