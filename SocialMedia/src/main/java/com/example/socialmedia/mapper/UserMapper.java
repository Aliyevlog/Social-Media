package com.example.socialmedia.mapper;

import com.example.socialmedia.dto.request.UserRegisterRequest;
import com.example.socialmedia.dto.response.UserResponse;
import com.example.socialmedia.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper
{
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public User map (UserRegisterRequest source)
    {
        User target = modelMapper.map(source, User.class);
        String passwordHash = passwordEncoder.encode(target.getPassword());
        target.setPassword(passwordHash);

        return target;
    }

    public UserResponse map (User source)
    {
        return modelMapper.map(source, UserResponse.class);
    }
}
