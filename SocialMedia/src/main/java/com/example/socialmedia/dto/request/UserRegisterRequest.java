package com.example.socialmedia.dto.request;

import com.example.socialmedia.enums.ERole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterRequest {
    @NotBlank(message = "{user.emptyName}")
    private String name;

    @NotBlank(message = "{user.emptySurname}")
    private String surname;

    @Email(message = "{user.invalidEmail}")
    private String email;

    @NotBlank(message = "{user.emptyUsername}")
    private String username;

    @NotBlank(message = "{user.emptyPassword}")
    @Size(min = 8, message = "{user.minPassword}")
    @Size(max = 15, message = "{user.maxPassword}")
    private String password;

    @NotNull(message = "{user.nullRole}")
    private ERole role;
}
