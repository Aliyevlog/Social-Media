package com.example.socialmedia.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class PageResponse<T> extends BaseResponse<List<T>>{
    private Integer page;
    private Integer pageSize;
    private Integer totalPages;
}
