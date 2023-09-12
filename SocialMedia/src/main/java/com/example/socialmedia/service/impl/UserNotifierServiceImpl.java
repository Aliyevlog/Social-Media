package com.example.socialmedia.service.impl;

import com.example.socialmedia.entity.User;
import com.example.socialmedia.service.EmailService;
import com.example.socialmedia.service.UserNotifierService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;

@Service
@RequiredArgsConstructor
public class UserNotifierServiceImpl implements UserNotifierService {
    private final EmailService emailService;

    @Value("classpath:templates/register-notify-template.html")
    private Resource template;

    @Override
    public void notifyRegistered(User user) throws IOException {
        Reader reader = new InputStreamReader(template.getInputStream(), StandardCharsets.UTF_8);
        String template = FileCopyUtils.copyToString(reader);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        template = template.replace("#name", user.getName())
                .replace("#date", simpleDateFormat.format(user.getCreatedAt()))
                .replace("#username", user.getUsername());

        emailService.sendEmail(user.getEmail(), "Welcome " + user.getName(), template);
    }
}
