package com.example.socialmedia.service.impl;

import com.example.socialmedia.exception.NotFoundException;
import com.example.socialmedia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException
    {
        try
        {
            return userService.getByUsername(username);
        } catch (NotFoundException e)
        {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
