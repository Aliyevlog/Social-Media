package com.example.socialmedia.repository;

import com.example.socialmedia.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
