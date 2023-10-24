package com.example.socialmedia.service.impl;

import com.example.socialmedia.entity.User;
import com.example.socialmedia.service.EmailService;
import com.example.socialmedia.service.UserNotifierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
@RequiredArgsConstructor
public class UserNotifierServiceImpl implements UserNotifierService {
    private final EmailService emailService;

    @Override
    public void notifyRegister(User user) {
        emailService.sendEmail(user.getEmail(), "Welcome to Friends", getSubjectForRegister(user));
    }

    private String getSubjectForRegister(User user) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String subject = """
                Hi %s,
                                
                Welcome to our platform. You have registered at %s with username @%s.
                """;
        return String.format(subject, user.getName(), simpleDateFormat.format(user.getCreatedAt()), user.getUsername());
    }
}
