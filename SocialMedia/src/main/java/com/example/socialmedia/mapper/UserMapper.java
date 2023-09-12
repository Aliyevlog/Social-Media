package com.example.socialmedia.mapper;

import com.example.socialmedia.dto.request.UpdateUserRequest;
import com.example.socialmedia.dto.request.UserRegisterRequest;
import com.example.socialmedia.dto.response.PageResponse;
import com.example.socialmedia.dto.response.UserResponse;
import com.example.socialmedia.entity.User;
import com.example.socialmedia.enums.ERole;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public void map(UpdateUserRequest source, User target) {
        modelMapper.map(source, target);
    }

    public User map(UserRegisterRequest source) {
        User target = modelMapper.map(source, User.class);
        String passwordHash = passwordEncoder.encode(target.getPassword());
        target.setPassword(passwordHash);
        target.setRole(ERole.USER);

        return target;
    }

    public UserResponse map(User source) {
        return modelMapper.map(source, UserResponse.class);
    }

    public PageResponse<UserResponse> map(Page<User> source) {
        PageResponse<UserResponse> target = new PageResponse<>();
        List<UserResponse> userResponses = new ArrayList<>();

        source.forEach(user -> userResponses.add(map(user)));
        target.setData(userResponses);
        target.setPage(source.getNumber() + 1);
        target.setPageSize(source.getSize());
        target.setTotalPages(source.getTotalPages());

        return target;
    }
}
