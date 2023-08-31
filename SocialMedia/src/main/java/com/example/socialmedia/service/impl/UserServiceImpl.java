package com.example.socialmedia.service.impl;

import com.example.socialmedia.config.MessageSourceConfig;
import com.example.socialmedia.entity.User;
import com.example.socialmedia.exception.NotFoundException;
import com.example.socialmedia.repository.UserRepository;
import com.example.socialmedia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.type.descriptor.java.LocaleJavaType;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    @Override
    public User register (User user)
    {
        user.setCreatedAt(new Date());

        return userRepository.save(user);
    }

    @Override
    public User getByUsername (String username) throws NotFoundException
    {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new NotFoundException(messageSource
                        .getMessage("user.notFound", null, LocaleContextHolder.getLocale())));
    }
}
