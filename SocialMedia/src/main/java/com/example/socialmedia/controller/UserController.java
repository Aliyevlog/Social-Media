package com.example.socialmedia.controller;

import com.example.socialmedia.config.SecurityConfig;
import com.example.socialmedia.dto.request.UpdateUserRequest;
import com.example.socialmedia.dto.response.BaseResponse;
import com.example.socialmedia.dto.response.UserResponse;
import com.example.socialmedia.entity.User;
import com.example.socialmedia.exception.AlreadyExistException;
import com.example.socialmedia.exception.NotFoundException;
import com.example.socialmedia.mapper.UserMapper;
import com.example.socialmedia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final SecurityConfig securityConfig;

    //TODO Get by id endpoint

    @GetMapping("/getByUsername/{username}")
    public ResponseEntity<BaseResponse<UserResponse>> getByUsername(@PathVariable String username) throws NotFoundException {
        User user = userService.getByUsername(username);
        UserResponse userResponse = userMapper.map(user);

        return ResponseEntity.ok(BaseResponse.success(userResponse));
    }

    @GetMapping("/me")
    public ResponseEntity<BaseResponse<UserResponse>> me() throws NotFoundException {
        String username = securityConfig.getLoggedInUsername();

        return getByUsername(username);
    }

    @PatchMapping
    public ResponseEntity<BaseResponse<UserResponse>> update(@RequestBody UpdateUserRequest request) throws NotFoundException, AlreadyExistException {
        User user = userService.getByUsername(securityConfig.getLoggedInUsername());
        userMapper.map(request, user);
        user = userService.update(user);
        UserResponse userResponse = userMapper.map(user);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(BaseResponse.success(userResponse));
    }
}
