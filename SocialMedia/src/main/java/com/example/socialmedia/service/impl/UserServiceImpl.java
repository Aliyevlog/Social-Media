package com.example.socialmedia.service.impl;

import com.example.socialmedia.config.SecurityConfig;
import com.example.socialmedia.dao.FileDao;
import com.example.socialmedia.dao.UserDao;
import com.example.socialmedia.dto.request.UpdateUserRequest;
import com.example.socialmedia.dto.request.UserRegisterRequest;
import com.example.socialmedia.dto.response.FileResponse;
import com.example.socialmedia.dto.response.PageResponse;
import com.example.socialmedia.dto.response.UserResponse;
import com.example.socialmedia.entity.File;
import com.example.socialmedia.entity.User;
import com.example.socialmedia.exception.AlreadyExistException;
import com.example.socialmedia.exception.NotFoundException;
import com.example.socialmedia.mapper.UserMapper;
import com.example.socialmedia.service.UserService;
import com.example.socialmedia.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final MessageSource messageSource;
    private final UserMapper userMapper;
    private final SecurityConfig securityConfig;
    private final FileDao fileDao;

    @Override
    public UserResponse register(UserRegisterRequest request) throws AlreadyExistException {
        User user = userMapper.map(request);
        checkUsernameExist(user);
        checkEmailExist(user);

        return userMapper.map(userDao.register(user));
    }

    @Override
    public UserResponse update(UpdateUserRequest updateUser)
            throws AlreadyExistException, NotFoundException {
        User user = userDao.getByUsername(securityConfig.getLoggedInUsername());
        userMapper.map(updateUser, user);

        checkUsernameExist(user);
        checkEmailExist(user);

        return userMapper.map(userDao.update(user));
    }

    @Override
    public UserResponse getByUsername(String username) throws NotFoundException {
        return userMapper.map(userDao.getByUsername(username));
    }

    @Override
    public UserResponse getLoggedInUser() throws NotFoundException {
        return getByUsername(securityConfig.getLoggedInUsername());
    }

    @Override
    public UserResponse getById(Long id) throws NotFoundException {
        return userMapper.map(userDao.getById(id));
    }

    @Override
    public PageResponse<UserResponse> getAll(String username, Integer page, Integer limit) {
        return userMapper.map(userDao.getAll(username, page, limit));
    }

    @Override
    public UserResponse uploadProfilePhoto(MultipartFile multipartFile)
            throws IOException, NotFoundException {
        User user = userDao.getByUsername(securityConfig.getLoggedInUsername());

        if (multipartFile != null && multipartFile.getOriginalFilename() != null) {
            String fileName = user.getId() + "_" + StringUtils.getFilename(multipartFile.getOriginalFilename());

            File file = File.builder()
                    .name(fileName)
                    .size(multipartFile.getSize())
                    .type(multipartFile.getContentType())
                    .build();
            file = fileDao.add(file);

            user.setPicture(file);
            user = userDao.update(user);

            FileUtil.saveFile(fileName, multipartFile);
        }

        return userMapper.map(user);
    }

    @Override
    public FileResponse getProfilePhoto(Long userId) throws NotFoundException, IOException {
        User user = userDao.getById(userId);

        java.io.File file = new java.io.File("files", user.getPicture().getName());
        byte[] arr = FileUtil.readFile(file);

        return FileResponse.builder()
                .arr(arr)
                .name(user.getPicture().getName())
                .type(user.getPicture().getType())
                .build();
    }

    private void checkUsernameExist(User user) throws AlreadyExistException {
        try {
            User foundUser = userDao.getByUsername(user.getUsername());
            if (foundUser != null && (user.getId() == null || !foundUser.getId().equals(user.getId())))
                throw new AlreadyExistException(messageSource.getMessage("user.existByUsername", null,
                        LocaleContextHolder.getLocale()));
        } catch (NotFoundException ignored) {
        }
    }

    private void checkEmailExist(User user) throws AlreadyExistException {
        try {
            User foundUser = userDao.getByEmail(user.getEmail());
            if (foundUser != null && (user.getId() == null || !foundUser.getId().equals(user.getId())))
                throw new AlreadyExistException(messageSource.getMessage("user.existByEmail", null,
                        LocaleContextHolder.getLocale()));
        } catch (NotFoundException ignored) {
        }
    }
}
