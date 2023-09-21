package com.example.socialmedia.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePostRequest {
    @NotBlank(message = "{post.emptyText}")
    private String text;
}
