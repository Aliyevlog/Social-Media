package com.example.socialmedia.dto.request;

import lombok.Data;

@Data
public class UserRegisterRequest
{
    private String name;
    private String surname;
    private String email;
    private String username;
    private String password;
}