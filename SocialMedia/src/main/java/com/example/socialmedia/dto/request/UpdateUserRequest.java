package com.example.socialmedia.dto.request;

import com.example.socialmedia.enums.EGender;
import lombok.Data;

@Data
public class UpdateUserRequest {
    private String name;
    private String surname;
    private String age;
    private EGender gender;
    private String address;
    private String phone;
    private String password;
}
