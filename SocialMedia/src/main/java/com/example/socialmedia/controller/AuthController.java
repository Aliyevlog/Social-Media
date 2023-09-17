package com.example.socialmedia.controller;

import com.example.socialmedia.dto.request.UserLoginRequest;
import com.example.socialmedia.dto.request.UserRegisterRequest;
import com.example.socialmedia.dto.response.BaseResponse;
import com.example.socialmedia.dto.response.JwtResponse;
import com.example.socialmedia.dto.response.UserResponse;
import com.example.socialmedia.entity.User;
import com.example.socialmedia.exception.AlreadyExistException;
import com.example.socialmedia.exception.NotFoundException;
import com.example.socialmedia.mapper.UserMapper;
import com.example.socialmedia.service.JwtService;
import com.example.socialmedia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<UserResponse>> register(@RequestBody UserRegisterRequest request) throws AlreadyExistException {
        User user = userMapper.map(request);
        user = userService.register(user);
        UserResponse userResponse = userMapper.map(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(userResponse));
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<JwtResponse>> login(@RequestBody UserLoginRequest request) throws NotFoundException {
        User user = userService.getByUsername(request.getUsername());

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()));

        JwtResponse jwtResponse = JwtResponse.builder()
                .accessToken(jwtService.generateAccessToken(user))
                .build();

        return ResponseEntity.ok(BaseResponse.success(jwtResponse));
    }
}
