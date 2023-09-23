package com.example.socialmedia.controller;

import com.example.socialmedia.dto.request.UpdateUserRequest;
import com.example.socialmedia.dto.response.BaseResponse;
import com.example.socialmedia.dto.response.FileResponse;
import com.example.socialmedia.dto.response.PageResponse;
import com.example.socialmedia.dto.response.UserResponse;
import com.example.socialmedia.exception.AlreadyExistException;
import com.example.socialmedia.exception.NotFoundException;
import com.example.socialmedia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<UserResponse>> getById(@PathVariable Long id) throws NotFoundException {
        UserResponse userResponse = userService.getById(id);

        return ResponseEntity.ok(BaseResponse.success(userResponse));
    }

    @GetMapping("/getByUsername/{username}")
    public ResponseEntity<BaseResponse<UserResponse>> getByUsername(@PathVariable String username) throws NotFoundException {
        UserResponse userResponse = userService.getByUsername(username);

        return ResponseEntity.ok(BaseResponse.success(userResponse));
    }

    @GetMapping("/me")
    public ResponseEntity<BaseResponse<UserResponse>> me() throws NotFoundException {
        UserResponse userResponse = userService.getLoggedInUser();

        return ResponseEntity.ok(BaseResponse.success(userResponse));
    }

    @PatchMapping
    public ResponseEntity<BaseResponse<UserResponse>> update(@RequestBody UpdateUserRequest request) throws NotFoundException, AlreadyExistException {
        UserResponse userResponse = userService.update(request);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(BaseResponse.success(userResponse));
    }

    @GetMapping
    public ResponseEntity<PageResponse<UserResponse>> getAll(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit) {
        PageResponse<UserResponse> pageResponse = userService.getAll(username, page, limit);

        return ResponseEntity.ok(pageResponse);
    }

    @PatchMapping("/uploadProfilePhoto")
    public ResponseEntity<BaseResponse<UserResponse>> upload(
            @RequestParam("picture") MultipartFile multipartFile)
            throws NotFoundException, IOException {
        UserResponse userResponse = userService.uploadProfilePhoto(multipartFile);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(BaseResponse.success(userResponse));
    }

    @GetMapping("/profilePhoto/{id}")
    public ResponseEntity<Resource> downloadProfilePhoto(@PathVariable Long id) throws NotFoundException, IOException {
        FileResponse fileResponse = userService.getProfilePhoto(id);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(fileResponse.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileResponse.getName() + "\"")
                .body(new ByteArrayResource(fileResponse.getArr()));
    }
}
