package com.example.socialmedia.controller;

import com.example.socialmedia.config.SecurityConfig;
import com.example.socialmedia.dto.request.UpdateUserRequest;
import com.example.socialmedia.dto.response.BaseResponse;
import com.example.socialmedia.dto.response.PageResponse;
import com.example.socialmedia.dto.response.UserResponse;
import com.example.socialmedia.entity.File;
import com.example.socialmedia.entity.User;
import com.example.socialmedia.exception.AlreadyExistException;
import com.example.socialmedia.exception.NotFoundException;
import com.example.socialmedia.mapper.UserMapper;
import com.example.socialmedia.service.FileService;
import com.example.socialmedia.service.UserService;
import com.example.socialmedia.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final SecurityConfig securityConfig;
    private final FileService fileService;

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<UserResponse>> getById(@PathVariable Long id) throws NotFoundException {
        User user = userService.getById(id);
        UserResponse userResponse = userMapper.map(user);

        return ResponseEntity.ok(BaseResponse.success(userResponse));
    }

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

    @GetMapping
    public ResponseEntity<PageResponse<UserResponse>> getAll(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit) {
        Page<User> users = userService.getAll(username, page, limit);
        PageResponse<UserResponse> pageResponse = userMapper.map(users);

        return ResponseEntity.ok(pageResponse);
    }

    @PatchMapping("/uploadProfilePhoto")
    public ResponseEntity<BaseResponse<UserResponse>> upload(
            @RequestParam("picture") MultipartFile multipartFile)
            throws NotFoundException, AlreadyExistException, IOException {
        User user = userService.getByUsername(securityConfig.getLoggedInUsername());

        if (multipartFile != null && multipartFile.getOriginalFilename() != null) {
            String fileName = user.getId() + "_" + StringUtils.getFilename(multipartFile.getOriginalFilename());

            File file = File.builder()
                    .name(fileName)
                    .size(multipartFile.getSize())
                    .type(multipartFile.getContentType())
                    .build();
            file = fileService.add(file);

            user.setPicture(file);
            user = userService.update(user);

            FileUtil.saveFile(fileName, multipartFile);
        }

        UserResponse userResponse = userMapper.map(user);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(BaseResponse.success(userResponse));
    }

    @GetMapping("/profilePhoto/{id}")
    public ResponseEntity<Resource> downloadProfilePhoto(@PathVariable Long id) throws NotFoundException, IOException {
        User user = userService.getById(id);

        java.io.File file = new java.io.File("files", user.getPicture().getName());
        byte[] arr = FileUtil.readFile(file);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(user.getPicture().getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + user.getPicture().getName() + "\"")
                .body(new ByteArrayResource(arr));
    }
}
