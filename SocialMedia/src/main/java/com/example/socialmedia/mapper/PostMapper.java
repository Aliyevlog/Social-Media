package com.example.socialmedia.mapper;

import com.example.socialmedia.dto.request.UpdateUserRequest;
import com.example.socialmedia.dto.request.UserRegisterRequest;
import com.example.socialmedia.dto.response.PostResponse;
import com.example.socialmedia.dto.response.UserResponse;
import com.example.socialmedia.entity.Post;
import com.example.socialmedia.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public void map(UpdateUserRequest source, Post target) {
        modelMapper.map(source, target);
    }


    public PostResponse map(Post source) {
        return modelMapper.map(source, PostResponse.class);
    }
}
