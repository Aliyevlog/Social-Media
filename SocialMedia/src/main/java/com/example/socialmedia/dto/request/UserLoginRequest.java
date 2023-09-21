package com.example.socialmedia.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserLoginRequest
{
    @NotBlank(message = "{user.emptyUsername}")
    private String username;

    @NotBlank(message = "{user.emptyPassword}")
    @Size(min = 8, message = "{user.minPassword}")
    @Size(max = 15, message = "{user.maxPassword}")
    private String password;
}
