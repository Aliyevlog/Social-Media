package com.example.socialmedia.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUsername (String accessToken);

    String generateAccessToken (UserDetails userDetails);

    Boolean isAccessTokenValid (String accessToken, UserDetails userDetails);
}
