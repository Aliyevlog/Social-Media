package com.example.socialmedia.service.impl;

import com.example.socialmedia.entity.File;
import com.example.socialmedia.repository.FileRepository;
import com.example.socialmedia.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;

    @Override
    public File add(File file) {
        return fileRepository.save(file);
    }

    @Override
    public File update(File file) {
        return fileRepository.save(file);
    }
}
