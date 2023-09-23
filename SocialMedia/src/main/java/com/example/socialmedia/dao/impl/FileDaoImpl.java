package com.example.socialmedia.dao.impl;

import com.example.socialmedia.dao.FileDao;
import com.example.socialmedia.entity.File;
import com.example.socialmedia.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileDaoImpl implements FileDao {
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
