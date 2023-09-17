package com.example.socialmedia.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class PostResponse {
    private Long id;
    private String text;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createdAt;
    private String fullName;
    private Long likeCount;
    private Long dislikeCount;
}
