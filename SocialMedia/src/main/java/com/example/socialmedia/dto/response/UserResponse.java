package com.example.socialmedia.dto.response;


import com.example.socialmedia.enums.EGender;
import lombok.Data;

import java.util.Date;

@Data
public class UserResponse
{
    private Integer id;
    private String name;
    private String surname;
    private Integer age;
    private EGender gender;
    private String address;
    private String phone;
    private String email;
    private String username;
    private Date createdAt;
}
