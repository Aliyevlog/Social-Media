package com.example.socialmedia.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseResponse<T> {
    private String errorMessage;
    private String successMessage;
    private T data;

    public static <T> BaseResponse<T> success(T data) {
        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.setData(data);
        baseResponse.setSuccessMessage("Operation is successfully!");

        return baseResponse;
    }

    public static <T> BaseResponse<T> error(String message) {
        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.setErrorMessage(message);

        return baseResponse;
    }
}
