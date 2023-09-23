package com.example.socialmedia.dao.impl;

import com.example.socialmedia.dao.UserDao;
import com.example.socialmedia.entity.User;
import com.example.socialmedia.exception.NotFoundException;
import com.example.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    @Override
    public User register(User user) {
        user.setCreatedAt(new Date());

        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getByUsername(String username) throws NotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new NotFoundException(messageSource
                        .getMessage("user.notFoundByUsername", null, LocaleContextHolder.getLocale())));
    }

    @Override
    public User getByEmail(String email) throws NotFoundException {
        return userRepository.findByEmail(email).orElseThrow(NotFoundException::new);
    }

    @Override
    public User getById(Long id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(() ->
                new NotFoundException(messageSource
                        .getMessage("user.notFoundById", null, LocaleContextHolder.getLocale())));
    }

    @Override
    public List<User> getByName(String name) {
        return userRepository.findByNameContaining(name);
    }

    @Override
    public Page<User> getAll(String username, Integer page, Integer limit) {
        Pageable pageable = page != null && limit != null ?
                PageRequest.of(page - 1, limit) :
                PageRequest.of(0, 10);
        username = username == null ? "" : username;

        return userRepository.findByUsernameContaining(username, pageable);
    }
}
