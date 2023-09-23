package com.example.socialmedia.service;

import com.example.socialmedia.exception.NotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUsername (String accessToken);

    String generateAccessToken (UserDetails userDetails);

    String generateAccessToken (String username) throws NotFoundException;

    Boolean isAccessTokenValid (String accessToken, UserDetails userDetails);
}
