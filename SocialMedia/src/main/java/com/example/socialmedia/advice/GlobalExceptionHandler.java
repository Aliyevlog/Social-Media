package com.example.socialmedia.advice;

import com.example.socialmedia.dto.response.BaseResponse;
import com.example.socialmedia.exception.AlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<Object> handleAlreadyExistException (AlreadyExistException ex)
    {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(BaseResponse.error(ex.getMessage()));
    }

    //TODO Not found exception
}